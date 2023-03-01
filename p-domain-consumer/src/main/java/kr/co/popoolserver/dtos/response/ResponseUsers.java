package kr.co.popoolserver.dtos.response;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.entity.user.model.Address;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import kr.co.popoolserver.enums.Gender;
import kr.co.popoolserver.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ResponseUsers {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TOKEN {
        @ApiModelProperty(example = "AccessToken")
        private String accessToken;

        @ApiModelProperty(example = "RefreshToken")
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_USER{
        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "YYmmDD")
        private String birth;

        @ApiModelProperty(example = "MALE or FEMALE")
        private Gender gender;

        @ApiModelProperty(example = "ROLE_USER")
        private UserRole userRole;

        @ApiModelProperty(example = "2022-01-01")
        private LocalDateTime createAt;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_CORPORATE{
        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "000-00-00000")
        private String businessNumber;

        @ApiModelProperty(example = "사업자명")
        private String businessName;

        @ApiModelProperty(example = "대표 이름")
        private String businessCeoName;

        @ApiModelProperty(example = "ROLE_CORPORATE")
        private UserRole userRole;

        @ApiModelProperty(example = "2022-01-01")
        private LocalDateTime createAt;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ_USER_DETAIL{
        private PhoneNumber phoneNumber;

        private Address address;

        private String email;
    }
}
