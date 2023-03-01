package kr.co.popoolserver.consumer.domain;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.persistence.entity.CorporateEntity;
import kr.co.popoolserver.persistence.entity.model.Address;
import kr.co.popoolserver.persistence.entity.model.PhoneNumber;
import kr.co.popoolserver.enums.UserRole;

public class CorporateCreators {

    public static CorporateEntity createCorporate() {
        return CorporateEntity.builder()
                .identity("corporate")
                .password("corporate")
                .businessAddress(createAddress())
                .businessCeoName("CEO")
                .businessEmail("corporate@naver.com")
                .businessNumber("00-000-000")
                .businessName("company")
                .businessPhoneNumber(new PhoneNumber("010-0000-0000"))
                .name("이름")
                .userRole(UserRole.ROLE_CORPORATE)
                .build();
    }

    public static CreateUsers.CREATE_CORPORATE createCorporateDto(){
        return CreateUsers.CREATE_CORPORATE.builder()
                .addr1("testAddress1")
                .addr2("testAddress1")
                .zipCode("000000")
                .businessCeoName("CEO")
                .businessEmail("corporate@naver.com")
                .businessNumber("00-000-000")
                .businessPhoneNumber("010-0000-0000")
                .identity("corporate")
                .password("corporate")
                .checkPassword("corporate")
                .businessName("company")
                .name("이름")
                .build();
    }

    public static CreateUsers.LOGIN createLoginDto(){
        return CreateUsers.LOGIN.builder()
                .identity("corporate")
                .password("corporate")
                .build();
    }

    public static UpdateUsers.UPDATE_CORPORATE updateCorporateDto(){
        return UpdateUsers.UPDATE_CORPORATE.builder()
                .businessCeoName("updateCeo")
                .businessName("updateCompany")
                .businessNumber("00-123-1234")
                .name("updateName")
                .build();
    }

    public static Address createAddress(){
        return Address.builder()
                .zipcode("000000")
                .address1("testAddress1")
                .address2("testAddress2")
                .build();
    }
}
