package kr.co.popoolserver.consumer.controller.user;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.enums.UserServiceName;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.entity.user.dto.UserCommonDto;
import kr.co.popoolserver.consumer.service.user.UserCommonService;
import kr.co.popoolserver.consumer.service.user.provider.UserCommonServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserCommonServiceProvider userCommonServiceProvider;
    private UserCommonService userCommonService;

    @ApiOperation("{USER or CORPORATE}/ 로그인")
    @PostMapping("/{userServiceName}/login")
    public ResponseFormat<UserCommonDto.TOKEN> login(@PathVariable UserServiceName userServiceName,
                                                     @RequestBody @Valid UserCommonDto.LOGIN login){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        return ResponseFormat.ok(userCommonService.login(login));
    }

    @ApiOperation("{USER or CORPORATE} / 회원 비밀번호 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/{userServiceName}/password")
    public ResponseFormat updateUserPassword(@PathVariable UserServiceName userServiceName,
                                             @RequestBody @Valid UserCommonDto.UPDATE_PASSWORD password){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        userCommonService.updatePassword(password);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 메일 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/email")
    public ResponseFormat updateUserEmail(@PathVariable UserServiceName userServiceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_EMAIL email){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        userCommonService.updateEmail(email);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 전화번호 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/phone")
    public ResponseFormat updateUserPhone(@PathVariable UserServiceName userServiceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_PHONE phone){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        userCommonService.updatePhone(phone);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 주소 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/address")
    public ResponseFormat updateUserAddress(@PathVariable UserServiceName userServiceName,
                                            @RequestBody @Valid UserCommonDto.UPDATE_ADDRESS address){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        userCommonService.updateAddress(address);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 주소 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{userServiceName}/address")
    public ResponseFormat<UserCommonDto.READ_ADDRESS> getUserAddress(@PathVariable UserServiceName userServiceName){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        return ResponseFormat.ok(userCommonService.getAddress());
    }

    @ApiOperation("{USER or CORPORATE} / 회원 메일 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{userServiceName}/email")
    public ResponseFormat<UserCommonDto.READ_EMAIL> getUserEmail(@PathVariable UserServiceName userServiceName){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        return ResponseFormat.ok(userCommonService.getEmail());
    }

    @ApiOperation("{USER or CORPORATE} / 전화번호 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{userServiceName}/phone")
    public ResponseFormat<UserCommonDto.READ_PHONE> getUserPhone(@PathVariable UserServiceName userServiceName){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        return ResponseFormat.ok(userCommonService.getPhone());
    }

    @ApiOperation("{USER or CORPORATE} / Redis Data 삭제")
    @DeleteMapping("/{userServiceName}/refresh")
    public ResponseFormat deleteRefreshToken(@PathVariable UserServiceName userServiceName,
                                             @RequestParam("identity") String identity){
        userCommonService = userCommonServiceProvider.getUserService(userServiceName);
        userCommonService.deleteRefreshToken(identity);
        return ResponseFormat.ok();
    }
}
