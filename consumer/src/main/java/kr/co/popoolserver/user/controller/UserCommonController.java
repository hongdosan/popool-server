package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.enums.ServiceName;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import kr.co.popoolserver.user.domain.service.RedisService;
import kr.co.popoolserver.user.domain.service.UserCommonService;
import kr.co.popoolserver.user.domain.service.provider.UserCommonServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCommonController {

    private final UserCommonServiceProvider userCommonServiceProvider;
    private final RedisService redisService;

    private UserCommonService userCommonService;

    @ApiOperation("{USER or CORPORATE}/ 로그인")
    @PostMapping("/{serviceName}/login")
    public ResponseFormat<UserCommonDto.TOKEN> login(@PathVariable ServiceName serviceName,
                                                     @RequestBody @Valid UserCommonDto.LOGIN login){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.login(login));
    }

    @ApiOperation("{USER or CORPORATE} / 회원 비밀번호 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/{serviceName}/password")
    public ResponseFormat updateUserPassword(@PathVariable ServiceName serviceName,
                                             @RequestBody @Valid UserCommonDto.UPDATE_PASSWORD password){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updatePassword(password);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 메일 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/email")
    public ResponseFormat updateUserEmail(@PathVariable ServiceName serviceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_EMAIL email){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updateEmail(email);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 전화번호 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/phone")
    public ResponseFormat updateUserPhone(@PathVariable ServiceName serviceName,
                                          @RequestBody @Valid UserCommonDto.UPDATE_PHONE phone){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updatePhone(phone);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 주소 변경 / 권한 : 본인 일반, 기업")
    @PutMapping("/address")
    public ResponseFormat updateUserAddress(@PathVariable ServiceName serviceName,
                                            @RequestBody @Valid UserCommonDto.UPDATE_ADDRESS address){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.updateAddress(address);
        return ResponseFormat.ok();
    }

    @ApiOperation("{USER or CORPORATE} / 회원 주소 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{serviceName}/address")
    public ResponseFormat<UserCommonDto.READ_ADDRESS> getUserAddress(@PathVariable ServiceName serviceName){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.getAddress());
    }

    @ApiOperation("{USER or CORPORATE} / 회원 메일 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{serviceName}/email")
    public ResponseFormat<UserCommonDto.READ_EMAIL> getUserEmail(@PathVariable ServiceName serviceName){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.getEmail());
    }

    @ApiOperation("{USER or CORPORATE} / 전화번호 정보 조회 / 권한 : 본인 일반, 기업")
    @GetMapping("/{serviceName}/phone")
    public ResponseFormat<UserCommonDto.READ_PHONE> getUserPhone(@PathVariable ServiceName serviceName){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        return ResponseFormat.ok(userCommonService.getPhone());
    }

    @ApiOperation("{USER or CORPORATE} / Redis Data 조회 (테스트를 위해 넣어놨음.)")
    @PostMapping("/{serviceName}/refresh")
    public ResponseFormat<String> getRefreshToken(@PathVariable ServiceName serviceName,
                                             @RequestParam("identity") String identity){
        return ResponseFormat.ok(redisService.getValue(identity));
    }

    @ApiOperation("{USER or CORPORATE} / Redis Data 삭제")
    @DeleteMapping("/{serviceName}/refresh")
    public ResponseFormat deleteRefreshToken(@PathVariable ServiceName serviceName,
                                             @RequestParam("identity") String identity){
        userCommonService = userCommonServiceProvider.getUserService(serviceName);
        userCommonService.deleteRefreshToken(identity);
        return ResponseFormat.ok();
    }
}
