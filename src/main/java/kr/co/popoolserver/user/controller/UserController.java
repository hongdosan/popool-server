package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
    public void updateUser(@RequestBody @Valid UserCreateDto userCreateDto){
        //TODO : update user
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
