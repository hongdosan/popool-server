package kr.co.popoolserver.consumer.service.user;

import kr.co.popoolserver.entity.user.dto.UserCommonDto;
import kr.co.popoolserver.enums.ServiceName;

public interface UserCommonService {

    //login
    UserCommonDto.TOKEN login(UserCommonDto.LOGIN login);

    //update
    void updatePassword(UserCommonDto.UPDATE_PASSWORD password);
    void updateEmail(UserCommonDto.UPDATE_EMAIL email);
    void updatePhone(UserCommonDto.UPDATE_PHONE phone);
    void updateAddress(UserCommonDto.UPDATE_ADDRESS address);

    //get
    UserCommonDto.READ_ADDRESS getAddress();
    UserCommonDto.READ_EMAIL getEmail();
    UserCommonDto.READ_PHONE getPhone();

    //delete
    void deleteRefreshToken(String identity);

    //common
    void checkIdentity(String identity);
    void checkPhoneNumber(String phoneNumber);
    void checkEmail(String email);
    void checkPassword(String password, String checkPassword);
    void checkEncodePassword(String password, String encodePassword);
    void checkDelete(String delYN);
    void checkReCreate(String delYN);
    Boolean canHandle(ServiceName serviceName);
}
