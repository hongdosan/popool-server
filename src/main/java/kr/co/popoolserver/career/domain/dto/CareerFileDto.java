package kr.co.popoolserver.career.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CareerFileDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CONVERT{
        @ApiModelProperty(example = "S3 URL")
        String s3Url;

        @ApiModelProperty(example = "파일명")
        String fileName;

        @ApiModelProperty(example = "S3 파일명")
        String s3FileName;

        @ApiModelProperty(example = "파일 확장자")
        String fileExtension;

        @ApiModelProperty(example = "파일 확장자 인덱스")
        int fileExtensionIndex;

        @ApiModelProperty(example = "파일 크기")
        long fileSize;
    }
}
