package kr.co.popoolserver.career.domain.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.popoolserver.career.domain.dto.S3MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3MultipartService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;
    @Value("{spring.s3.upload-dir")
    private String OBJECT_DIR;
    final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * Multipart Upload Start
     * Upload ID는 부분 업로드, 업로드 완료, 업로드 중지 등에 사용된다.
     * @param fileName
     * @return Upload ID, File Name
     */
    @Transactional
    public S3MultipartDto.UPLOAD initUpload(String fileName){
        String newFileName = createFileName(fileName);

        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(BUCKET_NAME)                        //Bucket 설정
                .key(createKey(newFileName))                //업로드 경로 설정
                .acl(ObjectCannedACL.PUBLIC_READ)           //Public_Read로 ACL 설정
                .expires(Instant.now().plusSeconds(60*20))  //객체 캐시 만료 시간 설정
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
     * @return 클라이언트에서 S3로 직접 업로드하기 위해 사용할 인증된 URL 요청
     */
    public String getUploadSignedUrl(S3MultipartDto.UPLOAD_SIGN_URL uploadSignUrl){
        UploadPartRequest request = UploadPartRequest.builder()
                .bucket(BUCKET_NAME)
                .key(createKey(uploadSignUrl.getFileName()))
                .uploadId(uploadSignUrl.getFileUploadId())
                .partNumber(uploadSignUrl.getPartNumber())
                .build();

        UploadPartPresignRequest preSignRequest = UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .uploadPartRequest(request)
                .build(); //미리 서명된 URL 요청

        return s3Presigner.presignUploadPart(preSignRequest).url().toString();
    }

    /**
     * Multipart Upload Completed Request Service
     * @param completedUpload
     * @return
     */
    public S3MultipartDto.UPLOAD_RESULT completeUpload(S3MultipartDto.COMPLETED_UPLOAD completedUpload){
        List<CompletedPart> completedParts = createCompletePart(completedUpload.getDetails());

        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build(); //Multipart 업로드 완료 요청 AWS 서버에 응답.

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                .bucket(BUCKET_NAME)                                    //bucket 설정
                .key(createKey(completedUpload.getFileName()))          //File Name 설정
                .uploadId(completedUpload.getUploadId())                //업로드 아이디
                .multipartUpload(completedMultipartUpload)              //파일 모든 부분 번호, Etag
                .build();

        CompleteMultipartUploadResponse response = s3Client.completeMultipartUpload(request);
        String objectKey = response.key();                                      //S3에 업로드된 파일 이름
        String url = amazonS3Client.getUrl(BUCKET_NAME, objectKey).toString();  //S3에 업로드된 파일 URL
        long fileSize = getFileSizeFromS3Url(response.bucket(), objectKey);     //파일 사이즈

        return S3MultipartDto.UPLOAD_RESULT.builder()
                .fileName(completedUpload.getFileName())
                .uri(url)
                .fileSize(fileSize)
                .build();
    }

    /**
     * Multipart Upload 중지
     * @param upload
     */
    public void abortUpload(S3MultipartDto.UPLOAD upload){
        AbortMultipartUploadRequest request = AbortMultipartUploadRequest.builder()
                .bucket(BUCKET_NAME)
                .key(createKey(upload.getFileName()))
                .uploadId(upload.getFileUploadId())
                .build();

        s3Client.abortMultipartUpload(request);
    }

    /**
     * 모든 부분들에 부분 번호와 Etag 설정
     * @param details
     * @return
     */
    private ArrayList<CompletedPart> createCompletePart(List<S3MultipartDto.UPLOAD_DETAIL> details){
        ArrayList<CompletedPart> completedParts = new ArrayList<>();

        for(S3MultipartDto.UPLOAD_DETAIL detail : details){
            CompletedPart completedPart = CompletedPart.builder()
                    .partNumber(detail.getPartNumber())
                    .eTag(detail.getAwsETag())
                    .build();
            completedParts.add(completedPart);
        }

        return completedParts;
    }

    /**
     * File Name 설정 Service
     * @param fileName
     * @return
     */
    private String createKey(String fileName){
        return OBJECT_DIR + "/" + fileName;
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

    /**
     * File Size Get Service
     * @param bucketName
     * @param fileName
     * @return
     */
    private long getFileSizeFromS3Url(String bucketName, String fileName){
        GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName, fileName);
        ObjectMetadata objectMetadata = amazonS3Client.getObjectMetadata(request);
        return objectMetadata.getContentLength();
    }
}
