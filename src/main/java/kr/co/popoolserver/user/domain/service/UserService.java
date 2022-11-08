package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserDeleteDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserUpdateDto;

public interface UserService {

    //login
    UserCreateDto.TOKEN login(UserCreateDto.LOGIN login);

    //create
    void signUp(UserCreateDto.CREATE create);

    //update
    void updateUser(UserUpdateDto.UPDATE update);
    void updatePassword(UserUpdateDto.PASSWORD password);
    void updateEmail(UserUpdateDto.EMAIL email);
    void updatePhone(UserUpdateDto.PHONE phone);
    void updateAddress(UserUpdateDto.ADDRESS address);

    //get
    UserGetDto.READ getUser();

    //delete
    void deleteUser(String password);
    void reCreateUser(UserDeleteDto.RE_CREATE reCreate);

    //common
    void checkIdentity(String identity);
    void checkPhoneNumber(String phoneNumber);
    void checkEmail(String email);
    void checkPassword(String password, String checkPassword);
    void checkEncodePassword(String password, String encodePassword);
    void checkDelete(String delYN);
    void checkReCreate(String delYN);
}
