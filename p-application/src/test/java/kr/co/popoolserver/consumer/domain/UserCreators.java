package kr.co.popoolserver.consumer.domain;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import kr.co.popoolserver.enums.Gender;
import kr.co.popoolserver.enums.UserRole;

public class UserCreators {
    public static UserEntity createUser() {
        return UserEntity.builder()
                .identity("user")
                .password("user")
                .email("user@naver.com")
                .name("이름")
                .birth("19980101")
                .phoneNumber(new PhoneNumber("010-0000-0000"))
                .gender(Gender.MALE)
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    public static CreateUsers.CREATE_USER createUserDto(){
        return CreateUsers.CREATE_USER.builder()
                .phoneNumber("010-1231-1231")
                .password("user")
                .identity("user")
                .gender("MALE")
                .checkPassword("user")
                .birth("19980000")
                .email("user@naver.com")
                .name("username")
                .build();
    }

    public static CreateUsers.LOGIN createLoginDto(){
        return CreateUsers.LOGIN.builder()
                .identity("user")
                .password("user")
                .build();
    }

    public static UpdateUsers.UPDATE_USER updateUserDto(){
        return UpdateUsers.UPDATE_USER.builder()
                .birth("19980101")
                .gender("MALE")
                .name("변경이름")
                .build();
    }

    public static UpdateUsers.UPDATE_PASSWORD updatePasswordDto(){
        return UpdateUsers.UPDATE_PASSWORD.builder()
                .originalPassword("user")
                .newPassword("newUser")
                .newCheckPassword("newUser")
                .build();
    }

    public static UpdateUsers.UPDATE_EMAIL updateEmailDto(){
        return UpdateUsers.UPDATE_EMAIL.builder()
                .email("newEmail")
                .originalPassword("user")
                .build();
    }

    public static UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumberDto(){
        return UpdateUsers.UPDATE_PHONE_NUMBER.builder()
                .newPhoneNumber("010-7777-7777")
                .originalPassword("user")
                .build();
    }

    public static UpdateUsers.UPDATE_ADDRESS updateAddressDto(){
        return UpdateUsers.UPDATE_ADDRESS.builder()
                .zipCode("000000")
                .addr1("testAddress1")
                .addr2("testAddress2")
                .originalPassword("user")
                .build();
    }

    public static UpdateUsers.DELETE deleteUserDto(){
        return UpdateUsers.DELETE.builder()
                .originalPassword("user")
                .build();
    }

    public static UpdateUsers.RESTORE restoreUserDto(){
        return UpdateUsers.RESTORE.builder()
                .identity("user")
                .originalPassword("user")
                .build();
    }
}
