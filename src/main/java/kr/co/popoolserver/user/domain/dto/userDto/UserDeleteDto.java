package kr.co.popoolserver.user.domain.dto.userDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UserDeleteDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class DELETE {
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class RE_CREATE {
        @ApiModelProperty(example = "복구할 아이디")
        @NotBlank(message = "복구할 아이디를 입력하세요.")
        private String identity;

        @ApiModelProperty(example = "복구 할 비밀번호")
        @NotBlank(message = "복구 할 비밀번호를 입력해주세요")
        private String originalPassword;
    }

}