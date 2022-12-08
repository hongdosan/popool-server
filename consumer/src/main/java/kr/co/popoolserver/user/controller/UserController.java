package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.infra.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @ApiOperation("일반 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid UserDto.CREATE create){
        userService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("AccessToken 재발급")
    @PostMapping("/refresh-token")
    public ResponseFormat<String> resetRefreshToken(@RequestHeader("refreshToken") String refreshToken){
        return ResponseFormat.ok(jwtProvider.createUserAccessToken(refreshToken));
    }

    @ApiOperation("일반 회원 정보 변경")
    @PutMapping
    public ResponseFormat updateUser(@RequestBody @Valid UserDto.UPDATE update){
        userService.updateUser(update);
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
