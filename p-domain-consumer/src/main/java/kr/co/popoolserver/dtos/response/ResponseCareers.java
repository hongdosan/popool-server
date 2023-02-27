package kr.co.popoolserver.dtos.response;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.entity.career.CareerFileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ResponseCareers {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_CAREER {
        @ApiModelProperty(example = "X년 x개월")
        private String officePeriod;

        @ApiModelProperty(example = "네카라쿠배당토")
        private String officeName;

        @ApiModelProperty(example = "문제 해결 경험 혹은 주도적으로 했던 경험 등")
        private String officeContext;

        @ApiModelProperty(example = "Backend, Spring, Java 등")
        private String officeSkill;

        @ApiModelProperty(example = "github.com")
        private String portfolioUrl;

        @ApiModelProperty(example = "create date")
        private LocalDateTime createAt;

        @ApiModelProperty(example = "작성자 이름")
        private String name;

        @ApiModelProperty(example = "프로필 사진")
        private CareerFileEntity careerFileEntity;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_CAREER_FILE{
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
