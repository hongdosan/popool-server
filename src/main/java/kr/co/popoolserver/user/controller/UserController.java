package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserDeleteDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserUpdateDto;
import kr.co.popoolserver.user.domain.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @ApiOperation("Login")
    @PostMapping("/login")
    public ResponseFormat<UserCreateDto.TOKEN> login(@RequestBody @Valid UserCreateDto.LOGIN login){
        return ResponseFormat.ok(userService.login(login));
    }

    @ApiOperation("일반 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid UserCreateDto.CREATE create){
        userService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 정보 변경")
    @PutMapping
    public ResponseFormat updateUser(@RequestBody @Valid UserUpdateDto.UPDATE update){
        userService.updateUser(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 비밀번호 변경")
    @PutMapping("/password")
    public ResponseFormat updateUserPassword(@RequestBody @Valid UserUpdateDto.PASSWORD password){
        userService.updatePassword(password);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 메일 변경")
    @PutMapping("/email")
    public ResponseFormat updateUserEmail(@RequestBody @Valid UserUpdateDto.EMAIL email){
        userService.updateEmail(email);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 전화번호 변경")
    @PutMapping("/phone")
    public ResponseFormat updateUserPhone(@RequestBody @Valid UserUpdateDto.PHONE phone){
        userService.updatePhone(phone);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 주소 변경")
    @PutMapping("/address")
    public ResponseFormat updateUserAddress(@RequestBody @Valid UserUpdateDto.ADDRESS address){
        userService.updateAddress(address);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 회원 정보 조회")
    @GetMapping
    public ResponseFormat<UserGetDto.READ> getUser(){
        return ResponseFormat.ok(userService.getUser());
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public ResponseFormat deleteUser(@RequestBody UserDeleteDto.DELETE delete){
        userService.deleteUser(delete);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 복구")
    @PutMapping("/reCreate")
    public ResponseFormat deleteUser(@RequestBody @Valid UserDeleteDto.RE_CREATE reCreate){
        userService.reCreateUser(reCreate);
        return ResponseFormat.ok();
    }
}
