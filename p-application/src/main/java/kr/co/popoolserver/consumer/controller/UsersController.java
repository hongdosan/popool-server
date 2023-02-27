package kr.co.popoolserver.consumer.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.consumer.service.user.provider.UserTypeProvider;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.consumer.security.ConsumerAuthenticationService;
import kr.co.popoolserver.consumer.service.user.CorporateService;
import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.consumer.service.user.UserService;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    private final CorporateService corporateService;

    private final ConsumerAuthenticationService consumerAuthenticationService;

    private final UserTypeProvider userTypeProvider;

    private UserCommonService userCommonService;

    @ApiOperation("일반 회원 등록 API")
    @PostMapping("/USER")
    public ResponseFormat<String> createUser(@RequestBody @Valid CreateUsers.CREATE_USER createUser){
        userService.createUser(createUser);
        return ResponseFormat.ok(createUser.getIdentity() + "님 회원가입 완료");
    }

    @ApiOperation("기업 회원 등록 API")
    @PostMapping("/CORPORATE")
    public ResponseFormat<String> createCorporate(@RequestBody @Valid CreateUsers.CREATE_CORPORATE createCorporate){
        corporateService.createCorporate(createCorporate);
        return ResponseFormat.ok(createCorporate.getIdentity() + "님 회원가입 완료");
    }

    @ApiOperation("로그인 API")
    @PostMapping("/{userType}/login")
    public ResponseFormat<ResponseUsers.TOKEN> login(@PathVariable(name = "userType") UserType userType,
                                                     @RequestBody @Valid CreateUsers.LOGIN login){
        userCommonService = userTypeProvider.getUserType(userType);
        return ResponseFormat.ok(userCommonService.login(login));
    }

    @ApiOperation("AccessToken 재발급 API")
    @PostMapping("/refresh-token")
    public ResponseFormat<String> resetRefreshToken(@RequestHeader("refreshToken") String refreshToken){
        //TODO token service
        return null;
    }

    @ApiOperation("본인 일반 정보 조회 API")
    @GetMapping("/USER")
    public ResponseFormat<ResponseUsers.READ_USER> getUser(){
        return ResponseFormat.ok(userService.getUser());
    }

    @ApiOperation("본인 기업 정보 조회 API")
    @GetMapping("/CORPORATE")
    public ResponseFormat<ResponseUsers.READ_CORPORATE> getCorporate(){
        return ResponseFormat.ok(corporateService.getCorporate());
    }

    @ApiOperation("세부 정보 조회 API : 주소, 전화번호, 이메일")
    @GetMapping("/{userName}/detail")
    public ResponseFormat<ResponseUsers.READ_DETAIL> getUsersDetail(@PathVariable(name = "userName") UserType userType){
        userCommonService = userTypeProvider.getUserType(userType);
        return ResponseFormat.ok(userCommonService.getUserDetail());
    }

    @ApiOperation("일반 회원 정보 변경 API")
    @PutMapping("/USER")
    public ResponseFormat<String> updateUser(@RequestBody @Valid UpdateUsers.UPDATE_USER updateUser){
        //TODO update user
        return null;
    }

    @ApiOperation("기업 회원 정보 변경 API")
    @PutMapping("/CORPORATE")
    public ResponseFormat<String> updateCorporate(@RequestBody @Valid UpdateUsers.UPDATE_CORPORATE updateCorporate){
        //TODO update corporate
        return null;
    }

    @ApiOperation("비밀번호 변경 API")
    @PutMapping("/{userName}/password")
    public ResponseFormat<String> updatePassword(@PathVariable(name = "userName") UserType userType,
                                             @RequestBody @Valid UpdateUsers.UPDATE_PASSWORD updatePassword){
//        userCommonService = userCommonServiceProvider.getUserService(userName);
//        userCommonService.updatePassword(password);
//        return ResponseFormat.ok();
        //TODO update password
        return null;
    }

    @ApiOperation("메일 변경 API")
    @PutMapping("/{userName}/email")
    public ResponseFormat<String> updateEmail(@PathVariable(name = "userName") UserType userType,
                                      @RequestBody @Valid UpdateUsers.UPDATE_EMAIL updateEmail){
//        userCommonService = userCommonServiceProvider.getUserService(userName);
//        userCommonService.updateEmail(email);
//        return ResponseFormat.ok();
        return null;
    }

    @ApiOperation("전화번호 변경 API")
    @PutMapping("/{userName}/phoneNumber")
    public ResponseFormat<String> updatePhoneNumber(@PathVariable(name = "userName") UserType userType,
                                                    @RequestBody @Valid UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber{
//        userCommonService = userCommonServiceProvider.getUserService(userName);
//        userCommonService.updateEmail(email);
//        return ResponseFormat.ok();
        return null;
    }

    @ApiOperation("주소 변경 API")
    @PutMapping("/{userName}/address")
    public ResponseFormat<String> updateAddress(@PathVariable(name = "userName") UserType userType,
                                                @RequestBody @Valid UpdateUsers.UPDATE_ADDRESS updateAddress){
//        userCommonService = userCommonServiceProvider.getUserService(userName);
//        userCommonService.updateEmail(email);
//        return ResponseFormat.ok();
        return null;
    }

    @ApiOperation("회원 탈퇴 복구 API")
    @PutMapping("/{userName}/restore")
    public ResponseFormat<String> restore(@PathVariable(name = "userName") UserType userType,
                                          @RequestBody @Valid UpdateUsers.RESTORE reCreate){
        //restore
        return null;
    }

    @ApiOperation("회원 탈퇴 API")
    @DeleteMapping
    public ResponseFormat<String> deleteUser(@PathVariable(name = "userName") UserType userType,
                                             @RequestBody UpdateUsers.DELETE delete){
        //delete
        return null;
    }
}
