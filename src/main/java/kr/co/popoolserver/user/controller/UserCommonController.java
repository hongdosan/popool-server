package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.domain.enums.ServiceName;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import kr.co.popoolserver.user.domain.service.UserCommonService;
import kr.co.popoolserver.user.domain.service.impl.UserCommonServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserCommonServiceProvider userCommonServiceProvider;
    private UserCommonService userCommonService;

    @ApiOperation("{USER or CORPORATE or ADMIN} / 로그인")
    @PostMapping("/{serviceName}/login")
    public ResponseFormat<UserCommonDto.TOKEN> login(@PathVariable ServiceName serviceName,
                                                     @RequestBody @Valid UserCommonDto.LOGIN login){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.login(login));
    }

    @ApiOperation("{USER or CORPORATE or ADMIN} / 회원 비밀번호 변경")
    @PutMapping("/{serviceName}/password")
    public ResponseFormat updateUserPassword(@PathVariable ServiceName serviceName,
                                             @RequestBody @Valid UserCommonDto.UPDATE_PASSWORD password){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updatePassword(password);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE or ADMIN} / 회원 메일 변경")
    @PutMapping("/email")
    public ResponseFormat updateUserEmail(@PathVariable ServiceName serviceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_EMAIL email){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updateEmail(email);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE or ADMIN} / 회원 전화번호 변경")
    @PutMapping("/phone")
    public ResponseFormat updateUserPhone(@PathVariable ServiceName serviceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_PHONE phone){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updatePhone(phone);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE or ADMIN} / 회원 주소 변경")
    @PutMapping("/address")
    public ResponseFormat updateUserAddress(@PathVariable ServiceName serviceName,
                                            @RequestBody @Valid UserCommonDto.UPDATE_ADDRESS address){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updateAddress(address);
        return ResponseFormat.ok();
    }
}
