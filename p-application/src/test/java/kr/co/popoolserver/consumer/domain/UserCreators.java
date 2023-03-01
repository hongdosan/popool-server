package kr.co.popoolserver.consumer.domain;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.entity.user.model.Address;
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

    public static UpdateUsers.UPDATE_ADDRESS createAddress(){
        return UpdateUsers.UPDATE_ADDRESS.builder()
                .zipCode("000000")
                .addr1("testAddress1")
                .addr2("testAddress2")
                .originalPassword("user")
                .build();
    }
}
