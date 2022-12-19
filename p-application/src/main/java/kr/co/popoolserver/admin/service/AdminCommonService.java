package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.enums.AdminServiceName;

public interface AdminCommonService {

    //common
    Boolean canHandle(AdminServiceName adminServiceName);
}
