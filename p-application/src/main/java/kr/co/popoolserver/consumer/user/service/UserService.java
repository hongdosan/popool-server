package kr.co.popoolserver.consumer.user.service;

import kr.co.popoolserver.dtos.users.UserDto;

public interface UserService extends UserCommonService{

    //create
    void signUp(UserDto.CREATE create);

    //update
    void updateUser(UserDto.UPDATE update);

    //get
    UserDto.READ getUser();

    //delete
    void deleteUser(UserDto.DELETE delete);
    void reCreateUser(UserDto.RE_CREATE reCreate);
}
