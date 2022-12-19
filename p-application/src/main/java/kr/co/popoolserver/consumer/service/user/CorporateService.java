package kr.co.popoolserver.consumer.service.user;

import kr.co.popoolserver.entity.users.dto.CorporateDto;

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
