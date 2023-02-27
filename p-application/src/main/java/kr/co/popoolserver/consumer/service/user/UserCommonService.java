package kr.co.popoolserver.consumer.service.user;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.dtos.UserCommonDto;
import kr.co.popoolserver.enums.UserName;

public interface UserCommonService {

    //login
    ResponseUsers.TOKEN login(CreateUsers.LOGIN login);

    //update
    void updatePassword(UpdateUsers.UPDATE_PASSWORD updatePassword);
    void updateEmail(UpdateUsers.UPDATE_EMAIL updateEmail);
    void updatePhone(UpdateUsers.UPDATE_PHONE updatePhone);
    void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress);

    //get
    ResponseUsers.READ_DETAIL getUserDetail();

    //delete
    void deleteRefreshToken(String identity);

    //common
    void isIdentity(String identity);
    void isPhoneNumber(String phoneNumber);
    void isEmail(String email);

    void checkPassword(String password, String checkPassword);
    void checkEncodePassword(String password, String encodePassword);
    void checkDelete(String delYN);
    void checkReCreate(String delYN);

    Boolean canHandle(UserName userName);
}
