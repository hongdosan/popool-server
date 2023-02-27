package kr.co.popoolserver.dtos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UpdateUsers {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class UPDATE_USER{
        @ApiModelProperty(example = "updateName")
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
    public static class UPDATE_CORPORATE{
        @ApiModelProperty(example = "HongDosan")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "000-00-00000")
        @NotBlank(message = "사업자 번호를 입력해주세요.")
        private String businessNumber;

        @ApiModelProperty(example = "사업자명")
        @NotBlank(message = "사업자명을 입력해주세요.")
        private String businessName;

        @ApiModelProperty(example = "대표 이름")
        @NotBlank(message = "대표 이름 입력해주세요.")
        private String businessCeoName;
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
    public static class UPDATE_PHONE_NUMBER {
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
    public static class DELETE {
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class RESTORE {
        @ApiModelProperty(example = "복구할 아이디")
        @NotBlank(message = "복구할 아이디를 입력하세요.")
        private String identity;

        @ApiModelProperty(example = "복구할 아이디의 삭제 전 비밀번호")
        @NotBlank(message = "복구할 비밀번호를 입력해주세요")
        private String originalPassword;
    }
}
