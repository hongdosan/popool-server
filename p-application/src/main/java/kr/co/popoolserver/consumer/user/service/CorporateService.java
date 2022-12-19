package kr.co.popoolserver.consumer.user.service;

import kr.co.popoolserver.dtos.users.CorporateDto;

public interface CorporateService extends UserCommonService{
    //create
    void signUp(CorporateDto.CREATE create);

    //update
    void updateCorporate(CorporateDto.UPDATE update);

    //get
    CorporateDto.READ getCorporate();

    //delete
    void deleteCorporate(CorporateDto.DELETE delete);
    void reCreateCorporate(CorporateDto.RE_CREATE reCreate);
}
