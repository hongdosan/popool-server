package kr.co.popoolserver.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.user.domain.dto.CorporateDto;
import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.domain.service.CorporateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/corporates")
@RequiredArgsConstructor
public class CorporateController {

    private final CorporateService corporateService;

    @ApiOperation("기업 회원 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid CorporateDto.CREATE create){
        corporateService.signUp(create);
        return ResponseFormat.ok();
    }
}
