package kr.co.popoolserver.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.common.domain.Address;
import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.domain.enums.Gender;
import kr.co.popoolserver.common.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserGetDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class READ{
        @ApiModelProperty(example = "사용자 아이디")
        private String identity;

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

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ADDRESS{
        private Address address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class EMAIL{
        private String email;
    }

}
