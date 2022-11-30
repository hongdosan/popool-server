package kr.co.popoolserver.career.domain.service;

import com.amazonaws.services.s3.AmazonS3Client;
import kr.co.popoolserver.career.domain.dto.S3MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3MultipartService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;
    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * Multipart Upload Start
     * Upload ID는 부분 업로드, 업로드 완료, 업로드 중지 등에 사용된다.
     * @param initUpload
     * @return Upload ID, File Name
     */
    @Transactional
    public S3MultipartDto.UPLOAD initUpload(S3MultipartDto.INIT_UPLOAD initUpload){
        String newFileName = createFileName(initUpload.getOriginFileName());

        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(initUpload.getTargetBucket()) //Bucket 설정
                .key(initUpload.getTargetObjectDir() + "/" + newFileName) //업로드 경로 설정
                .acl(ObjectCannedACL.PUBLIC_READ) //Public_Read로 ACL 설정
                .expires(Instant.now().plusSeconds(60*20)) //객체 캐시 만료 시간 설정
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);

        return S3MultipartDto.UPLOAD.builder()
                .fileName(newFileName)
                .fileUploadId(response.uploadId())
                .build();
    }

    /**
     * 부분 업로드할 서명된 URL 발급 요청
     * @param uploadSignUrl
     * @return
     */
    public S3MultipartDto.PRE_SIGNED_URL getUploadSignedUrl(S3MultipartDto.UPLOAD_SIGN_URL uploadSignUrl){
        UploadPartRequest request = UploadPartRequest.builder()
                .bucket(uploadSignUrl.getTargetBucket())
                .key(uploadSignUrl.getTargetObjectDir() + "/" + uploadSignUrl.getFileName())
                .uploadId(uploadSignUrl.getFileUploadId())
                .partNumber(uploadSignUrl.getPartNumber())
                .build();

        UploadPartPresignRequest preSignRequest = UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .uploadPartRequest(request)
                .build(); //미리 서명된 URL 요청

        //클라이언트에서 S3로 직접 업로드하기 위해 사용할 인증된 URL 요청
        PresignedUploadPartRequest preSignedUploadRequest = s3Presigner.presignUploadPart(preSignRequest);

        return S3MultipartDto.PRE_SIGNED_URL.builder()
                .preSignedRequestUrl(preSignedUploadRequest.url().toString())
                .build();
    }

    /**
     * File의 Type Check Service
     * @param originFileName
     * @return
     */
    private String fileTypeCheck(String originFileName){
        return originFileName.substring(originFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR)).toLowerCase();
    }

    /**
     * File Name Create (NOW Time Use)
     * @param originFileName
     * @return
     */
    private String createFileName(String originFileName){
        return System.currentTimeMillis() + fileTypeCheck(originFileName);
    }
}
