package kr.co.popoolserver.consumer.service.user.provider;

import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeProvider {

    private final List<UserCommonService> userCommonServiceList;

    public UserCommonService getUserType(UserType userType){
        return userCommonServiceList.stream()
                .filter(type -> type.canHandle(userType))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_SERVICE));
    }
}
