package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.CorporateDto;
import kr.co.popoolserver.user.domain.dto.UserDto;

public interface CorporateService extends UserCommonService{
    //create
    void signUp(UserDto.CREATE create);

    //update
    void updateUser(UserDto.UPDATE update);

    //get
    CorporateDto.READ getUser();

    //delete
    void deleteUser(CorporateDto.DELETE delete);
    void reCreateUser(CorporateDto.RE_CREATE reCreate);
}
