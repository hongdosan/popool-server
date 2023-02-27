package kr.co.popoolserver.dtos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UpdateCareers {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class UPDATE_CAREER {
        @ApiModelProperty(example = "1")
        @NotBlank(message = "Career Entity Domain PK Value")
        private Long id;

        @ApiModelProperty(example = "X년 x개월")
        @NotBlank(message = "근무 기간을 입력해주세요.")
        private String officePeriod;

        @ApiModelProperty(example = "네카라쿠배당토")
        @NotBlank(message = "근무 회사명을 입력해주세요.")
        private String officeName;

        @ApiModelProperty(example = "문제 해결 경험 혹은 주도적으로 했던 경험 등")
        @NotBlank(message = "근무하면서 겪었던 경험을 적어주세요.")
        private String officeContext;

        @ApiModelProperty(example = "Backend, Spring, Java 등")
        @NotBlank(message = "근무하면서 맡았던 업무 기술을 적어주세요.")
        private String officeSkill;

        @ApiModelProperty(example = "github.com")
        @NotBlank(message = "나만의 자기소개서 주소가 있다면 적어주세요.")
        private String portfolioUrl;
    }
}
