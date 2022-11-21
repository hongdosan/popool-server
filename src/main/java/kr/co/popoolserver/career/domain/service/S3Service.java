package kr.co.popoolserver.career.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.common.infra.error.exception.EmptyFileException;
import kr.co.popoolserver.common.infra.error.exception.FileUploadFailedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {

    private final AmazonS3 amazonS3;
    private final CareerFileService careerFileService;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;
    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * File S3, DB 업로드
     * @param multipartFile
     * @return
     */
    @Transactional
    public String uploadS3(MultipartFile multipartFile){
        validateFileExists(multipartFile);

        UserEntity userEntity = UserThreadLocal.get();
        CareerFileDto.CONVERT convertFile = convertPutS3(multipartFile);
        careerFileService.createCareerFile(convertFile, userEntity);

        return convertFile.getS3Url();
    }

    /**
     * 파일화 및 S3 업로드
     * @param multipartFile
     * @return
     */
    private CareerFileDto.CONVERT convertPutS3(MultipartFile multipartFile){

        ObjectMetadata objectMetadata = new ObjectMetadata();
        String originalFileName = multipartFile.getOriginalFilename();
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);

        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String s3FileName = UUID.randomUUID() + "-" + fileName + fileExtension;

        try (InputStream inputStream = multipartFile.getInputStream()){
            objectMetadata.setContentLength(inputStream.available());
            amazonS3.putObject(BUCKET_NAME, s3FileName, inputStream, objectMetadata);
        }catch (IOException e){
            throw new FileUploadFailedException(ErrorCode.FAIL_FILE_UPLOAD);
        }

        return CareerFileDto.CONVERT.builder()
                .fileName(fileName)
                .s3FileName(s3FileName)
                .s3Url(amazonS3.getUrl(BUCKET_NAME, s3FileName).toString())
                .fileExtension(fileExtension)
                .fileExtensionIndex(fileExtensionIndex)
                .fileSize(objectMetadata.getContentLength())
                .build();
    }

    /**
     * S3 삭제
     */
    @Transactional
    public void removeS3(){
        //TODO : S3 데이터 삭제
    }

    /**
     * 파일 여부 체크
     * @param multipartFile
     */
    private void validateFileExists(MultipartFile multipartFile){
        if(multipartFile.isEmpty()) throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
    }
}
