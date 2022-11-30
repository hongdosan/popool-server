package kr.co.popoolserver.career.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class S3MultipartDto {

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

        private Integer partNumber;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class COMPLETED_UPLOAD{
        private String fileName;

        private String uploadId;

        private List<UPLOAD_DETAIL> details;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD_RESULT{
        private String fileName;

        private String uri;

        private long fileSize;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD_DETAIL{
        private Integer partNumber;

        private String awsETag;
    }

}
