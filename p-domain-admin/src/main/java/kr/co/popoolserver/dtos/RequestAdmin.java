package kr.co.popoolserver.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class RequestAdmin {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE_ADMIN{
        @ApiModelProperty(example = "id1234")
        @NotBlank(message = "아이디를 입력하세요.")
        private String identity;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "확인 비밀번호를 입력하세요.")
        private String checkPassword;

        @ApiModelProperty(example = "HongDosan")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LOGIN_ADMIN{
        @ApiModelProperty(example = "User ID")
        @NotBlank(message = "ID")
        private String identity;

        @ApiModelProperty(example = "User PW")
        @NotBlank(message = "PW")
        private String password;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DELETE_ADMIN {
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }
}
