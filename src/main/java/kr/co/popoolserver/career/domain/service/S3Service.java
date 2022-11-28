package kr.co.popoolserver.career.domain.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.dto.S3Dto;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.EmptyFileException;
import kr.co.popoolserver.common.infra.error.exception.FileUploadFailedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.user.domain.entity.UserEntity;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final CareerFileService careerFileService;

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

        UserEntity userEntity = UserThreadLocal.get();
        CareerFileDto.CONVERT convertFile = convert(multipartFile)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_UPLOAD));

        convertFile.setFileSize(putS3(multipartFile, convertFile.getS3FileName()));
        careerFileService.createCareerFile(convertFile, userEntity);

        return convertFile.getS3Url();
    }

    /**
     * file Download Path
     * @param fileS3Url
     * @return
     */
    public S3Dto.DOWNLOAD download(String fileS3Url){
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(BUCKET_NAME, fileS3Url));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        try {
            byte[] bytes = IOUtils.toByteArray(objectInputStream);
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setContentType(contentType(fileS3Url));
            httpHeaders.setContentLength(bytes.length);

            String[] fileArr = fileS3Url.split("/");
            String type = fileArr[fileArr.length - 1];
            String fileName = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");

            httpHeaders.setContentDispositionFormData("attachment", fileName); //File Name 지정

            return S3Dto.DOWNLOAD.builder()
                    .bytes(bytes)
                    .httpHeaders(httpHeaders)
                    .build();
        }catch (IOException e){
            throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
        }
    }

    /**
     * File Delete Service
     * @param fileS3Path : 폴더명/FileName.File_확장자
     */
    @Transactional
    public void removeS3(String fileS3Path){
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, fileS3Path));
        }catch (AmazonServiceException e){
            e.printStackTrace();
        }catch (SdkClientException e){
            e.printStackTrace();
        }
        careerFileService.deleteCareerFile();
    }

    /**
     * File S3 업로드
     * @param multipartFile
     * @param s3FileName
     * @return
     */
    private long putS3(MultipartFile multipartFile, String s3FileName){
        try (InputStream inputStream = multipartFile.getInputStream()){
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());

            amazonS3Client.putObject(
                    new PutObjectRequest(BUCKET_NAME, s3FileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            return objectMetadata.getContentLength();
        }catch (IOException e){
            throw new FileUploadFailedException(ErrorCode.FAIL_FILE_UPLOAD);
        }
    }

    /**
     * 파일화
     * @param multipartFile
     * @return
     */
    private Optional<CareerFileDto.CONVERT> convert(MultipartFile multipartFile){

        String originalFileName = multipartFile.getOriginalFilename();
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);

        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String s3FileName = UUID.randomUUID() + "-" + fileName + fileExtension;

        return Optional.ofNullable(CareerFileDto.CONVERT.builder()
                .fileName(fileName)
                .s3FileName(s3FileName)
                .s3Url(amazonS3Client.getUrl(BUCKET_NAME, s3FileName).toString())
                .fileExtension(fileExtension)
                .fileExtensionIndex(fileExtensionIndex)
                .build());
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
