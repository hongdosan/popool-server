package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.UserEntity;
import kr.co.popoolserver.user.domain.dto.UserDto;

public interface UserService {

    //login
    UserDto.TOKEN login(UserDto.LOGIN login);

    //create
    void signUp(UserDto.CREATE create);

    //update
    void updateUser(UserDto userDto);

    //get
    void getUser();

    //delete
    void deleteUser();

    //common
    void checkIdentity(String identity);
    void checkPhoneNumber(String phoneNumber);
    void checkPassword(String password, String checkPassword);
    void checkEncodePassword(String password, String encodePassword);
    void checkDelete(String delYN);
}
