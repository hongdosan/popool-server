package kr.co.popoolserver.entity.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.entity.user.model.Address;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UserCommonDto {

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
    public static class UPDATE_EMAIL{
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
    public static class UPDATE_PHONE {
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
    public static class UPDATE_PASSWORD{
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
    public static class UPDATE_ADDRESS {
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

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_PHONE{
        private PhoneNumber phoneNumber;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_ADDRESS{
        private Address address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_EMAIL{
        private String email;
    }

}
