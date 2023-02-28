package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.dtos.RequestAdmin;
import kr.co.popoolserver.dtos.ResponseAdmin;
import kr.co.popoolserver.entitiy.AdminEntity;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.admin.service.AdminUserService;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private final RedisService redisService;

    private final AdminUserService adminUserService;

    @ApiOperation("관리자 로그인 API")
    @PostMapping("/login")
    public ResponseFormat<ResponseAdmin.TOKEN_ADMIN> login(@RequestBody @Valid RequestAdmin.LOGIN_ADMIN loginAdmin) {
        return ResponseFormat.ok(adminUserService.login(loginAdmin));
    }

    @ApiOperation("관리자 등록 API")
    @PostMapping("/create")
    public ResponseFormat<String> createAdmin(@RequestBody @Valid RequestAdmin.CREATE_ADMIN createAdmin){
        adminUserService.createAdmin(createAdmin);
        return ResponseFormat.ok(createAdmin.getName() + " 관리자 등록 완료");
    }

    @ApiOperation("관리자 이름 조회 API")
    @GetMapping
    public ResponseFormat<List<ResponseAdmin.READ_ADMIN>> getAdmin(){
        return ResponseFormat.ok(adminUserService.getAllAdmin());
    }

    @ApiOperation("관리자 삭제 API")
    @DeleteMapping
    public ResponseFormat<String> deleteAdmin(@RequestBody RequestAdmin.DELETE_ADMIN deleteAdmin){
        adminUserService.deleteAdmin(deleteAdmin);
        return ResponseFormat.ok("관리자 삭제 완료");
    }

    @ApiOperation("Redis Data 조회 API")
    @PostMapping("/refresh")
    public ResponseFormat<String> getRefreshToken(@RequestParam("identity") String identity){
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        return ResponseFormat.ok(redisService.getValue(identity));
    }
}