package kr.co.popoolserver.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.domain.PhoneNumber;
import kr.co.popoolserver.enums.Gender;
import kr.co.popoolserver.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {

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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UPDATE{
        @ApiModelProperty(example = "hhj")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "19980101")
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birth;

        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 입력하세요.")
        private String gender;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ{
        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "YYmmDD")
        private String birth;

        @ApiModelProperty(example = "010-xxxx-xxxx")
        private PhoneNumber phoneNumber;

        @ApiModelProperty(example = "MALE or FEMALE")
        private Gender gender;

        @ApiModelProperty(example = "ROLE_USER")
        private UserRole userRole;

        @ApiModelProperty(example = "2022-01-01")
        private LocalDateTime createAt;
    }

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
