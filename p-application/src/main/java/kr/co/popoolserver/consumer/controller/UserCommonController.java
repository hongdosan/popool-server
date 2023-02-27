package kr.co.popoolserver.consumer.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.consumer.service.user.provider.UserServiceProvider;
import kr.co.popoolserver.enums.UserName;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserServiceProvider userServiceProvider;
    private UserCommonService userCommonService;

    @ApiOperation("{USER or CORPORATE} / Redis Data 삭제")
    @DeleteMapping("/{userName}/refresh")
    public ResponseFormat deleteRefreshToken(@PathVariable UserName userName,
                                             @RequestParam("identity") String identity){
        userCommonService = userServiceProvider.getUserService(userName);
        userCommonService.deleteRefreshToken(identity);
        return ResponseFormat.ok();
    }
}
