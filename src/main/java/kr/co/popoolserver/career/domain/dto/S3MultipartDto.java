package kr.co.popoolserver.career.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD_RESULT{

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UPLOAD_DETAIL{

    }

}
