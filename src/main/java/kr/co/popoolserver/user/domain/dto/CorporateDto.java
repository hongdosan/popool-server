package kr.co.popoolserver.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CorporateDto {
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
        @Size(min = 5, max = 17, message = "비밀번호는 5~17자를 입력하세요.")
        private String password;

        @ApiModelProperty(example = "pw1234")
        @NotBlank(message = "확인 비밀번호를 입력하세요.")
        @Size(min = 5, max = 17, message = "확인 비밀번호는 5~17자를 입력하세요.")
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
    public static class UPDATE{
        //TODO : Corporate Dto
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ{
        //TODO : Corporate Dto
    }


    @Builder
    @Getter
    @AllArgsConstructor
    public static class DELETE {
        //TODO : Corporate Dto
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class RE_CREATE {
        //TODO : Corporate Dto
    }
}
