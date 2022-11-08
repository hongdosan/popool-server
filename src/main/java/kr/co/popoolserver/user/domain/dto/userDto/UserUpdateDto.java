package kr.co.popoolserver.user.domain.dto.userDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UserUpdateDto {

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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class EMAIL{
        @ApiModelProperty(example = "example@email.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PHONE {
        @ApiModelProperty(example = "010-XXXX-XXXX")
        @NotBlank(message = "휴대폰 번호를 입력해주세요")
        private String newPhoneNumber;

        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class PASSWORD{
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;

        @ApiModelProperty(example = "변경할 비밀번호")
        @NotBlank(message = "변경할 비밀번호를 입력해주세요")
        private String newPassword;

        @ApiModelProperty(example = "변경 비밀번호 확인")
        @NotBlank(message = "확인 비밀번호를 입력해주세요")
        private String newCheckPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ADDRESS {
        @ApiModelProperty(example = "12345")
        @NotBlank(message = "우편번호를 입력해주세요")
        private String zipCode;

        @ApiModelProperty(example = "서울특별시 강남구 선릉로 627")
        @NotBlank(message = "기본 주소를 입력해주세요")
        private String addr1;

        @ApiModelProperty(example = "101호")
        @NotBlank(message = "상세 주소를 입력해주세요")
        private String addr2;

        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }
}
