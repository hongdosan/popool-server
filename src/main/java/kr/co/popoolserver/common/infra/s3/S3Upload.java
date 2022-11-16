package kr.co.popoolserver.common.infra.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.career.domain.service.CareerFileService;
import kr.co.popoolserver.common.infra.error.exception.EmptyFileException;
import kr.co.popoolserver.common.infra.error.exception.FileUploadFailedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
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
public class S3Upload {

    private final AmazonS3 amazonS3;
    private final CareerFileService careerFileService;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;
    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * s3FileName : S3에 저장되는 파일 이름 (UUID.randomUUID(): 중복 방지)
     * ObjectMetadata : 업로드하는 파일의 사이즈를 S3에 알려주기 위함.
     * putObject : S3 API Method 를 사용해 파일 Stream 을 열어 S3에 파일 업로드.
     * getUrl Method : 해당 메소드를 통해 S3에 업로드된 사진 URL 가져옴.
     * @param multipartFile
     * @return
     */
    @Transactional
    public String uploadFile(MultipartFile multipartFile){
        validateFileExists(multipartFile);

        long fileSize;
        String originalFilename = multipartFile.getOriginalFilename();
        int fileExtensionIndex = originalFilename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFilename.substring(fileExtensionIndex);
        String fileName = originalFilename.substring(0, fileExtensionIndex);
        String s3FileName = UUID.randomUUID() + "-" + fileName + fileExtension;

        try (InputStream inputStream = multipartFile.getInputStream()){
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            amazonS3.putObject(BUCKET_NAME, s3FileName, inputStream, objectMetadata);
            fileSize = objectMetadata.getContentLength();
        }catch (IOException e){
            throw new FileUploadFailedException(ErrorCode.FAIL_FILE_UPLOAD);
        }

        String s3Url = amazonS3.getUrl(BUCKET_NAME, s3FileName).toString();
        careerFileService.createFile(CareerFileEntity.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(s3Url)
                .fileExtension(fileExtension)
                .fileExtensionIndex(fileExtensionIndex)
                .build());

        return s3Url;
    }

    /**
     * 파일 여부 체크
     * @param multipartFile
     */
    private void validateFileExists(MultipartFile multipartFile){
        if(multipartFile.isEmpty()) throw new EmptyFileException(ErrorCode.FAIL_FILE_EMPTY);
    }
}
