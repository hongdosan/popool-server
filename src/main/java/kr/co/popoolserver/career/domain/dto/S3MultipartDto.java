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
    public static class S3_UPLOAD{
        private String fileName;

        private String fileUploadId;
    }
}
