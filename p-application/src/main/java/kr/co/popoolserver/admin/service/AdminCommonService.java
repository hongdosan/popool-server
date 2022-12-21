package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.enums.AdminServiceName;

public interface AdminCommonService {

    //common
    void checkAdmin();
    void checkEncodePassword(String password, String encodePassword);
    Boolean canHandle(AdminServiceName adminServiceName);
}
