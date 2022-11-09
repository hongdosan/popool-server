package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.domain.enums.ServiceName;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.domain.service.UserCommonService;
import kr.co.popoolserver.user.domain.service.UserService;
import kr.co.popoolserver.user.domain.service.impl.UserCommonServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserCommonServiceProvider userCommonServiceProvider;

    @ApiOperation("Login")
    @PostMapping("/{serviceName}/login")
    public ResponseFormat<UserCommonDto.TOKEN> login(@PathVariable ServiceName serviceName,
                                                     @RequestBody @Valid UserCommonDto.LOGIN login){
        UserCommonService userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.login(login));
    }

    @ApiOperation("일반 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid UserDto.CREATE create){
        userService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 정보 변경")
    @PutMapping
    public ResponseFormat updateUser(@RequestBody @Valid UserDto.UPDATE update){
        userService.updateUser(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 비밀번호 변경")
    @PutMapping("/password")
    public ResponseFormat updateUserPassword(@RequestBody @Valid UserCommonDto.UPDATE_PASSWORD password){
        userService.updatePassword(password);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 메일 변경")
    @PutMapping("/email")
    public ResponseFormat updateUserEmail(@RequestBody @Valid UserCommonDto.UPDATE_EMAIL email){
        userService.updateEmail(email);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 전화번호 변경")
    @PutMapping("/phone")
    public ResponseFormat updateUserPhone(@RequestBody @Valid UserCommonDto.UPDATE_PHONE phone){
        userService.updatePhone(phone);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 주소 변경")
    @PutMapping("/address")
    public ResponseFormat updateUserAddress(@RequestBody @Valid UserCommonDto.UPDATE_ADDRESS address){
        userService.updateAddress(address);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 회원 정보 조회")
    @GetMapping
    public ResponseFormat<UserDto.READ> getUser(){
        return ResponseFormat.ok(userService.getUser());
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public ResponseFormat deleteUser(@RequestBody UserDto.DELETE delete){
        userService.deleteUser(delete);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 복구")
    @PutMapping("/reCreate")
    public ResponseFormat deleteUser(@RequestBody @Valid UserDto.RE_CREATE reCreate){
        userService.reCreateUser(reCreate);
        return ResponseFormat.ok();
    }
}
