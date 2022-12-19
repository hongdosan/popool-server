package kr.co.popoolserver.consumer.service.user.provider;

import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.enums.UserServiceName;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCommonServiceProvider {

    private final List<UserCommonService> userCommonServiceList;

    public UserCommonService getUserService(UserServiceName userServiceName){
        UserCommonService userCommonService = userCommonServiceList.stream()
                .filter(service -> service.canHandle(userServiceName))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_SERVICE));
        return userCommonService;
    }
}
