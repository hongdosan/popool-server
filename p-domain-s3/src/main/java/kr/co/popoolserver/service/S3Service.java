package kr.co.popoolserver.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import kr.co.popoolserver.dtos.S3Dto;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.error.exception.EmptyFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    private String fileDir;

    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * 서버가 시작할 때 프로파일에 맞는 파일 경로를 설정해줌
     */
    @PostConstruct
    private void init(){
        this.fileDir = System.getProperty("user.dir");
    }

    public S3Dto.CONVERT uploadS3(MultipartFile multipartFile,
                                  String dirName){

        File uploadFile = validateFileExists(multipartFile)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_CONVERT));

        //file name 설정
        String fileName = createFileName(dirName + "/", multipartFile.getOriginalFilename(), uploadFile.getName());

        String uploadImageUrl = putS3(uploadFile, fileName);

        S3Dto.CONVERT convert = createS3Convert(fileName, uploadImageUrl, uploadFile.length());

        removeNewFile(uploadFile);

        return convert;
    }

    private String putS3(File uploadFile,
                         String fileName){
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET_NAME, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET_NAME, fileName).toString();
    }

    /**
     * Local File Image Delete Service
     * @param targetFile
     */
    private void removeNewFile(File targetFile){
        if(targetFile.delete())
            return;
    }

    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String createFileName(String dirName,
                                  String fileName,
                                  String uploadFileName){
        return dirName + UUID.randomUUID().toString().concat(getFileExtension(fileName)) + uploadFileName;
    }

    private S3Dto.CONVERT createS3Convert(String fileName,
                                          String uploadImageUrl,
                                          long fileSize){
        return S3Dto.CONVERT.builder()
                .fileName(fileName)
                .fileUrl(uploadImageUrl)
                .fileSize(fileSize)
                .fileExtension(contentType(fileName).toString())
                .build();
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
     * S3 File Delete Service
     * @param fileName
     */
    public void deleteS3(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, fileName));
    }

    /**
     * File Download Service
     * @param fileName
     * @return
     */
    public S3Dto.DOWNLOAD downloadS3(String fileName){
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(BUCKET_NAME, fileName));
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
     * File Name 지정
     * @param fileName
     * @return
     */
    private String downloadS3Name(String fileName) {
        String[] fileArr = fileName.split("/");
        String type = fileArr[fileArr.length-1];

        try {
            return URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");
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

        if(type.equals("txt"))
            return MediaType.TEXT_PLAIN;
        else if(type.equals("png"))
            return MediaType.IMAGE_PNG;
        else if(type.equals("jpg"))
            return MediaType.IMAGE_JPEG;
        else
            return MediaType.APPLICATION_OCTET_STREAM;
    }

    /**
     * 파일 여부, 확장자 체크
     * @param multipartFile
     */
    private Optional<File> validateFileExists(MultipartFile multipartFile){
        if(multipartFile.isEmpty()) {
            throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        }

        if(ObjectUtils.isEmpty(multipartFile.getContentType())) {
            throw new EmptyFileException(ErrorCode.FAIL_FILE_INVALID_CONTENT_TYPE);
        }

        return convert(multipartFile);
    }

    /**
     * 파일 변환 후 local에 파일 저장 Service
     * @param multipartFile
     * @return
     */
    private Optional<File> convert(MultipartFile multipartFile){
        try {
            String storeFileName = createFileName(multipartFile.getOriginalFilename());
            File file = new File(fileDir + storeFileName);
            multipartFile.transferTo(file);

            return Optional.ofNullable(file);
        }catch (IOException e){
            throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        }
    }
}
