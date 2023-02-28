package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.enums.AdminType;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.UserDefineException;
import kr.co.popoolserver.error.model.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AdminCommonService {

    //common
    Boolean canHandle(AdminType adminType);

    default void checkPassword(String password,
                               String checkPassword) {
        if(!password.equals(checkPassword)) {
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    default void checkEncodePassword(String password,
                                     String encodePassword,
                                     PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(password, encodePassword)) {
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    default void checkAdminRole(UserRole userRole){
        if(!userRole.equals(UserRole.ROLE_ADMIN)) {
            throw new UserDefineException(ErrorCode.FAIL_USER_ROLE);
        }
    }
}
