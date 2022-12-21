package kr.co.popoolserver.consumer.controller.user;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.consumer.security.ConsumerAuthenticationService;
import kr.co.popoolserver.entity.user.dto.UserDto;
import kr.co.popoolserver.consumer.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConsumerAuthenticationService consumerAuthenticationService;

    @ApiOperation("일반 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid UserDto.CREATE create){
        userService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("AccessToken 재발급 / 권한 : 본인 일반")
    @PostMapping("/refresh-token")
    public ResponseFormat<String> resetRefreshToken(@RequestHeader("refreshToken") String refreshToken){
        return ResponseFormat.ok(consumerAuthenticationService.createUserAccessToken(refreshToken));
    }

    @ApiOperation("회원 정보 변경 / 권한 : 본인 일반")
    @PutMapping
    public ResponseFormat updateUser(@RequestBody @Valid UserDto.UPDATE update){
        userService.updateUser(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 정보 조회 / 권한 : 본인 일반")
    @GetMapping
    public ResponseFormat<UserDto.READ> getUser(){
        return ResponseFormat.ok(userService.getUser());
    }

    @ApiOperation("회원 탈퇴 / 권한 : 본인 일반")
    @DeleteMapping
    public ResponseFormat deleteUser(@RequestBody UserDto.DELETE delete){
        userService.deleteUser(delete);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 복구")
    @PutMapping("/reCreate")
    public ResponseFormat reCreateUser(@RequestBody @Valid UserDto.RE_CREATE reCreate){
        userService.reCreateUser(reCreate);
        return ResponseFormat.ok();
    }
}
