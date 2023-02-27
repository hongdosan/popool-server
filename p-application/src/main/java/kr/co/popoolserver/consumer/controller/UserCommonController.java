package kr.co.popoolserver.consumer.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.consumer.service.user.provider.UserTypeProvider;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserTypeProvider userTypeProvider;
    private UserCommonService userCommonService;

    @ApiOperation("{USER or CORPORATE} / Redis Data 삭제")
    @DeleteMapping("/{userType}/refresh")
    public ResponseFormat deleteRefreshToken(@PathVariable UserType userType,
                                             @RequestParam("identity") String identity){
        userCommonService = userTypeProvider.getUserType(userType);
        userCommonService.deleteRefreshToken(identity);
        return ResponseFormat.ok();
    }
}