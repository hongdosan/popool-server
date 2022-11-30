package kr.co.popoolserver.career.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class S3MultipartDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class INIT_UPLOAD{
        private String originFileName;

        private String targetBucket;

        private String targetObjectDir;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD{
        private String fileName;

        private String fileUploadId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD_SIGN_URL{
        private String fileName;

        private String fileUploadId;

        private String targetBucket;

        private String targetObjectDir;

        private Integer partNumber;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PRE_SIGNED_URL{
        private String preSignedRequestUrl;
    }
}
