package kr.co.popoolserver.user.domain.service.provider;

import kr.co.popoolserver.common.domain.enums.ServiceName;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.user.domain.service.UserCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCommonServiceProvider {

    private final List<UserCommonService> userCommonServiceList;

    public UserCommonService getUserService(ServiceName serviceName){
        UserCommonService userCommonService = userCommonServiceList.stream()
                .filter(service -> service.canHandle(serviceName))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_SERVICE));
        return userCommonService;
    }
}