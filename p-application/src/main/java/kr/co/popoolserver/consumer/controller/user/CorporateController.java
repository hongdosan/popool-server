package kr.co.popoolserver.consumer.controller.user;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.consumer.security.ConsumerAuthenticationService;
import kr.co.popoolserver.entity.user.dto.CorporateDto;
import kr.co.popoolserver.consumer.service.user.CorporateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/corporates")
@RequiredArgsConstructor
public class CorporateController {

    private final ConsumerAuthenticationService consumerAuthenticationService;
    private final CorporateService corporateService;

    @ApiOperation("기업 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid CorporateDto.CREATE create){
        corporateService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("AccessToken 재발급 / 권한 : 기업 본인")
    @PostMapping("/refresh-token")
    public ResponseFormat<String> resetRefreshToken(@RequestHeader("refreshToken") String refreshToken){
        return ResponseFormat.ok(consumerAuthenticationService.createCorporateAccessToken(refreshToken));
    }

    @ApiOperation("회원 정보 변경 / 권한 : 기업 본인")
    @PutMapping
    public ResponseFormat updateCorporate(@RequestBody @Valid CorporateDto.UPDATE update){
        corporateService.updateCorporate(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 정보 조회 / 권한 : 기업 본인")
    @GetMapping
    public ResponseFormat<CorporateDto.READ> getCorporate(){
        return ResponseFormat.ok(corporateService.getCorporate());
    }

    @ApiOperation("회원 탈퇴 / 권한 : 기업 본인")
    @DeleteMapping
    public ResponseFormat deleteCorporate(@RequestBody CorporateDto.DELETE delete){
        corporateService.deleteCorporate(delete);
        return ResponseFormat.ok();
    }

    @ApiOperation("기업 회원 복구")
    @PutMapping("/reCreate")
    public ResponseFormat reCreateCorporate(@RequestBody @Valid CorporateDto.RE_CREATE reCreate){
        corporateService.reCreateCorporate(reCreate);
        return ResponseFormat.ok();
    }
}
