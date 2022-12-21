package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.enums.AdminServiceName;

public interface AdminCommonService {

    //common
    void checkAdmin();
    Boolean canHandle(AdminServiceName adminServiceName);
}
