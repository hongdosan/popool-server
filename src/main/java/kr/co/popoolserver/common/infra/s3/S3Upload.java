package kr.co.popoolserver.common.infra.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Upload {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        //S3에 저장되는 파일 이름 (UUID.randomUUID : 중복되지 않기 위함)
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        //ObjectMetadata : 업로드하는 파일의 사이즈를 S3에 알려주기 위함.
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        //putObject : S3 API Method 를 사용해 파일 Stream 을 열어 S3에 파일 업로드.
        amazonS3.putObject(BUCKET_NAME, s3FileName, multipartFile.getInputStream(), objectMetadata);

        //getUrl Method 를 통해 S3에 업로드된 사진 URL 가져옴.
        return amazonS3.getUrl(BUCKET_NAME, s3FileName).toString();
    }
}
