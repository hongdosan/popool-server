package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.UserCreateDto;

public interface UserService {

    //login
    default UserCreateDto.TOKEN login(UserCreateDto.LOGIN login){ return null; }

    //create
    default void signUp(UserCreateDto.CREATE create){}

    //update
    default void updateUser(UserCreateDto userCreateDto){}

    //get
    default void getUser(){}

    //delete
    default void deleteUser(){}

    //common
    default void checkIdentity(String identity){}
    default void checkPhoneNumber(String phoneNumber){}
    default void checkPassword(String password, String checkPassword){}
    default void checkEncodePassword(String password, String encodePassword){}
    default void checkDelete(String delYN){}
}
