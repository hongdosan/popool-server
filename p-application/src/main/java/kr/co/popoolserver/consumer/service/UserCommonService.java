package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.exception.BadRequestException;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.service.RedisService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserCommonService {

    //login
    ResponseUsers.TOKEN login(CreateUsers.LOGIN login);

    //update
    void updatePassword(UpdateUsers.UPDATE_PASSWORD updatePassword);
    void updateEmail(UpdateUsers.UPDATE_EMAIL updateEmail);
    void updatePhoneNumber(UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber);
    void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress);

    //get
    ResponseUsers.READ_USER_DETAIL getUserDetail();

    //delete
    void deleteUser(UpdateUsers.DELETE delete);
    void restoreUser(UpdateUsers.RESTORE restore);

    //common
    void isIdentity(String identity);
    void isPhoneNumber(String phoneNumber);
    void isEmail(String email);

    Boolean canHandle(UserType userType);

    /**
     * PW, Check PW equals check
     * @param password : user pw
     * @param checkPassword : check pw
     */
    default void checkPassword(String password, String checkPassword){
        if(!password.equals(checkPassword)){
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    /**
     * Login Encode PW Check
     * @param password : request pw
     * @param encodePassword : encode pw
     */
    default void checkEncodePassword(String password,
                                     String encodePassword,
                                     PasswordEncoder passwordEncoder){
        if(!passwordEncoder.matches(password, encodePassword)) {
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    default void checkDelete(Integer isDelted){
        if(isDelted.equals(1)) {
            throw new BusinessLogicException(ErrorCode.DELETED_USER);
        }
    }

    default void checkRestore(Integer isDelted){
        if(isDelted.equals(0)) {
            throw new BadRequestException("해당 회원은 탈퇴되어있지 않습니다.");
        }
    }

    default void deleteRefreshToken(String identity,
                                    RedisService redisService){
        redisService.deleteData(identity);
    }
}
