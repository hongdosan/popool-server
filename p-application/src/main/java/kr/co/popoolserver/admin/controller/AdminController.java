package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.entitiy.AdminDto;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.admin.service.AdminService;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final RedisService redisService;
    private final AdminService adminService;

    @ApiOperation("관리자 로그인 / 권한 : 관리자")
    @PostMapping("/login")
    public ResponseFormat<AdminDto.TOKEN> login(@RequestBody @Valid AdminDto.LOGIN login) {
        return ResponseFormat.ok(adminService.login(login));
    }

    @ApiOperation("관리자 회원가입 / 권한 : 관리자")
    @PostMapping("/signUp")
    public ResponseFormat signUp(@RequestBody @Valid AdminDto.CREATE create){
        adminService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("관리자 이름 조회 / 권한 : 관리자")
    @GetMapping
    public ResponseFormat<AdminDto.READ> getAdmin(){
        return ResponseFormat.ok(adminService.getAdmin());
    }

    @ApiOperation("관리자 회원 삭제 / 권한 : 관리자")
    @DeleteMapping
    public ResponseFormat deleteAdmin(@RequestBody AdminDto.DELETE delete){
        adminService.deleteAdmin(delete);
        return ResponseFormat.ok();
    }

    @ApiOperation("Redis Data 조회 (테스트를 위해 넣어놨음.) : 관리자")
    @PostMapping("/refresh")
    public ResponseFormat<String> getRefreshToken(@RequestParam("identity") String identity){
        adminService.checkAdmin();
        return ResponseFormat.ok(redisService.getValue(identity));
    }
}
