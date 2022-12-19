package kr.co.popoolserver.entity.career.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CareerFileDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_INFO{
        @ApiModelProperty(example = "파일명")
        private String fileName;

        @ApiModelProperty(example = "파일 확장자")
        private String fileExtension;

        @ApiModelProperty(example = "파일 크기")
        private long fileSize;

        @ApiModelProperty(example = "User ID")
        private String identity;

        @ApiModelProperty(example = "생성일")
        private LocalDateTime createAt;
    }
}
