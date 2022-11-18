package kr.co.popoolserver.career.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CareerDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CREATE {
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
        private String popolUrl;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ {
        @ApiModelProperty(example = "X년 x개월")
        private String officePeriod;

        @ApiModelProperty(example = "네카라쿠배당토")
        private String officeName;

        @ApiModelProperty(example = "문제 해결 경험 혹은 주도적으로 했던 경험 등")
        private String officeContext;

        @ApiModelProperty(example = "Backend, Spring, Java 등")
        private String officeSkill;

        @ApiModelProperty(example = "github.com")
        private String popolUrl;

        @ApiModelProperty(example = "create date")
        private LocalDateTime createAt;

        @ApiModelProperty(example = "작성자 이름")
        private String name;

        @ApiModelProperty(example = "프로필 사진")
        private CareerFileEntity careerFileEntity;
    }
}