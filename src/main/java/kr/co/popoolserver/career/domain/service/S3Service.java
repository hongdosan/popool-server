
package kr.co.popoolserver.career.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import kr.co.popoolserver.career.domain.dto.S3Dto;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.EmptyFileException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;
    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * File Upload Service
     * @param multipartFile
     * @return
     */
    @Transactional
    public String uploadS3(MultipartFile multipartFile){
        validateFileExists(multipartFile);
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        InputStream inputStream;

        try {
            inputStream = multipartFile.getInputStream();
            objectMetadata.setContentLength(inputStream.available());
        } catch (IOException e){
            throw new BusinessLogicException(ErrorCode.FAIL_FILE_UPLOAD);
        }

        amazonS3.putObject(BUCKET_NAME, fileName, inputStream, objectMetadata);
        return amazonS3.getUrl(BUCKET_NAME, fileName).toString();
    }

    /**
     * File Delete Service
     * @param fileName : 폴더명/FileName.File_확장자
     */
    @Transactional
    public void deleteS3(String fileName){
        amazonS3.deleteObject(BUCKET_NAME, fileName);
    }

    /**
     * File Download Service
     * @param fileName
     * @return
     */
    public S3Dto.DOWNLOAD downloadS3(String fileName){
        S3Object s3Object = amazonS3.getObject(BUCKET_NAME, fileName);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        try {
            byte[] bytes = IOUtils.toByteArray(objectInputStream);
            String downloadFileName = downloadS3Name(fileName);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(contentType(fileName));
            httpHeaders.setContentLength(bytes.length);
            httpHeaders.setContentDispositionFormData("attachment", downloadFileName);

            return S3Dto.DOWNLOAD.builder()
                    .bytes(bytes)
                    .httpHeaders(httpHeaders)
                    .build();
        }catch (IOException e){
            throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        }
    }

    /**
     * file name 난수화 Service
     * @param fileName
     * @return
     */
    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    /**
     * file 형식 Check Service
     * @param fileName
     * @return
     */
    private String getFileExtension(String fileName){
        try {
            return fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR));
        }catch (StringIndexOutOfBoundsException e){
            throw new BusinessLogicException(ErrorCode.FAIL_FILE_INVALID_NAME);
        }
    }

    /**
     * File Name 지정
     * @param fileName
     * @return
     */
    private String downloadS3Name(String fileName) {
        String[] fileArr = fileName.split("/");
        String type = fileArr[fileArr.length-1];

        try {
            return URLEncoder.encode(type, "UTF-8")
                    .replaceAll("\\+", "%20");
        }catch (IOException e){
            throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        }
    }

    /**
     * Content Type 구분
     * @param keyName
     * @return
     */
    private MediaType contentType(String keyName){
        String[] keyNameArr = keyName.split("\\.");
        String type = keyNameArr[keyNameArr.length-1];
        if(type.equals("txt")) return MediaType.TEXT_PLAIN;
        else if(type.equals("png")) return MediaType.IMAGE_PNG;
        else if(type.equals("jpg")) return MediaType.IMAGE_JPEG;
        else return MediaType.APPLICATION_OCTET_STREAM;
    }

    /**
     * 파일 여부, 확장자 체크
     * @param multipartFile
     */
    private void validateFileExists(MultipartFile multipartFile){
        if(multipartFile.isEmpty()) throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        if(ObjectUtils.isEmpty(multipartFile.getContentType())) throw new EmptyFileException(ErrorCode.FAIL_FILE_INVALID_CONTENT_TYPE);
    }
}
