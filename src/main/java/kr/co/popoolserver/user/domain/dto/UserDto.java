package kr.co.popoolserver.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LOGIN{
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
    public static class TOKEN {
        @ApiModelProperty(example = "AccessToken")
        private String accessToken;

        @ApiModelProperty(example = "RefreshToken")
        private String refreshToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CREATE{
        @ApiModelProperty(example = "id1234")
        @NotBlank(message = "아이디를 입력하세요.")
        @Size(min = 5, max = 17, message = "아이디는 5~17자를 입력하세요.")
        private String identity;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 5, max = 17, message = "비밀번호는 5~17  자를 입력하세요.")
        private String password;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "확인 비밀번호를 입력하세요.")
        @Size(min = 5, max = 17, message = "확인 비밀번호는 5~17  자를 입력하세요.")
        private String checkPassword;

        @ApiModelProperty(example = "HongDosan")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "19980101")
        @NotBlank(message = "생년월일을 입력해주세요.")
        @Size(min = 8, max = 8, message = "생년월일은 8자리로 입력해주세요.")
        private String birth;

        @ApiModelProperty(example = "010-1111-1111")
        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        private String phone;

        @ApiModelProperty(example = "MALE")
        @NotBlank(message = "성별을 입력해주세요.(MALE or FEMALE)")
        private String gender;
    }
}
