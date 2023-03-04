package kr.co.popoolserver.dtos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateUsers {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE_USER{
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

        @ApiModelProperty(example = "19980101")
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birth;

        @ApiModelProperty(example = "010-1111-1111")
        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        private String phoneNumber;

        @ApiModelProperty(example = "example@naver.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @ApiModelProperty(example = "MALE")
        @NotBlank(message = "성별을 입력해주세요.(MALE or FEMALE)")
        private String gender;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE_CORPORATE{
        @ApiModelProperty(example = "id1234")
        @NotBlank(message = "아이디를 입력하세요.")
        private String identity;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "확인 비밀번호를 입력하세요.")
        private String checkPassword;

        @ApiModelProperty(example = "010-0000-0000")
        @NotBlank(message = "회사 전화 번호를 입력해주세요.")
        private String businessPhoneNumber;

        @ApiModelProperty(example = "000-00-00000")
        @NotBlank(message = "사업자 번호를 입력해주세요.")
        private String businessNumber;

        @ApiModelProperty(example = "사업자명")
        @NotBlank(message = "사업자명을 입력해주세요.")
        private String businessName;

        @ApiModelProperty(example = "대표 이름")
        @NotBlank(message = "대표 이름 입력해주세요.")
        private String businessCeoName;

        @ApiModelProperty(example = "회사 메일")
        @NotBlank(message = "회사 메일을 입력해주세요.")
        private String businessEmail;

        @ApiModelProperty(example = "12345")
        @NotBlank(message = "회사 우편번호를 입력해주세요")
        private String zipCode;

        @ApiModelProperty(example = "서울특별시 강남구 선릉로 627")
        @NotBlank(message = "회사 주소를 입력해주세요")
        private String addr1;

        @ApiModelProperty(example = "101호")
        @NotBlank(message = "회사 상세 주소를 입력해주세요")
        private String addr2;

        @ApiModelProperty(example = "HongDosan")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LOGIN{
        @ApiModelProperty(example = "User ID")
        @NotBlank(message = "ID")
        private String identity;

        @ApiModelProperty(example = "User PW")
        @NotBlank(message = "PW")
        private String password;
    }
}
