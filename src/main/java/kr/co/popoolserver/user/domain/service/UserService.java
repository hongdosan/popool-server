package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.UserDto;

public interface UserService {

    //create
    void signUp(UserDto.CREATE create);

    //update
    void updateUser(UserDto userDto);

    //get
    void getUser();

    //delete
    void deleteUser();

    //common
    public void checkIdentity(String identity);
    public void checkPhoneNumber(String phoneNumber);
    public void checkPassword(String password, String checkPassword);
}
