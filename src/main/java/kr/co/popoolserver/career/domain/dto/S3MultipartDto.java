package kr.co.popoolserver.career.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class S3MultipartDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class INIT_UPLOAD{
        @ApiModelProperty(example = "Upload할 파일명")
        private String fileName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PRE_SIGNED_URL{
        private String preSignedUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPLOAD{
        @ApiModelProperty(example = "Server에서 생성한 파일명")
        private String fileName;

        @ApiModelProperty(example = "S3 Upload ID")
        private String uploadId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPLOAD_SIGN_URL{
        @ApiModelProperty(example = "init-upload에서 얻어온 새 파일명")
        private String fileName;

        @ApiModelProperty(example = "init-upload에서 얻어온 upload ID")
        private String fileUploadId;

        @ApiModelProperty(example = "upload할 파일 조각 Number (1부터 시작)")
        private Integer partNumber;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class COMPLETED_UPLOAD{
        @ApiModelProperty(example = "init-upload에서 얻어온 새 파일명")
        private String fileName;

        @ApiModelProperty(example = "init-upload에서 얻어온 upload ID")
        private String uploadId;

        @ApiModelProperty(example = "Upload할 파일 ETag, PartNumber 데이터 목록")
        private List<UPLOAD_DETAIL> details;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPLOAD_RESULT{
        @ApiModelProperty(example = "File Name")
        private String fileName;

        @ApiModelProperty(example = "S3 URL")
        private String uri;

        @ApiModelProperty(example = "원본 파일 사이즈 (Byte)")
        private long fileSize;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPLOAD_DETAIL{
        private Integer partNumber;

        private String awsETag;
    }

}
