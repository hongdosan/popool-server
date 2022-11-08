package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserUpdateDto;

public interface UserService {

    //login
    UserCreateDto.TOKEN login(UserCreateDto.LOGIN login);

    //create
    void signUp(UserCreateDto.CREATE create);

    //update
    void updateUser(UserUpdateDto.UPDATE update);

    //get
    UserGetDto.READ getUser();

    //delete
    void deleteUser();

    //common
    void checkIdentity(String identity);
    void checkPhoneNumber(String phoneNumber);
    void checkPassword(String password, String checkPassword);
    void checkEncodePassword(String password, String encodePassword);
    void checkDelete(String delYN);
}
