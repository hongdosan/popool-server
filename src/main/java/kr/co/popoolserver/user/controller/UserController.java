package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserUpdateDto;
import kr.co.popoolserver.user.domain.service.UserService;
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
    public void updateUser(@RequestBody @Valid UserUpdateDto.UPDATE update){
        userService.updateUser(update);
    }

    @ApiOperation("일반 회원 비밀번호 변경")
    @PutMapping("/password")
    public void updateUserPassword(@RequestBody @Valid UserUpdateDto.PASSWORD password){
        userService.updatePassword(password);
    }

    @ApiOperation("일반 회원 메일 변경")
    @PutMapping("/email")
    public void updateUserEmail(@RequestBody @Valid UserUpdateDto.EMAIL email){
        userService.updateEmail(email);
    }

    @ApiOperation("일반 회원 전화번호 변경")
    @PutMapping("/phone")
    public void updateUserPhone(@RequestBody @Valid UserUpdateDto.PHONE phone){
        userService.updatePhone(phone);
    }

    @ApiOperation("일반 회원 주소 변경")
    @PutMapping("/address")
    public void updateUserAddress(@RequestBody @Valid UserUpdateDto.ADDRESS address){
        userService.updateAddress(address);
    }

    @ApiOperation("본인 회원 정보 조회")
    @GetMapping
    public UserGetDto.READ getUser(){
        return userService.getUser();
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public void deleteUser(){
        //TODO delete user
    }
}
