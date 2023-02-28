package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.admin.service.AdminUserService;
import kr.co.popoolserver.dtos.S3Dto;
import kr.co.popoolserver.entitiy.AdminEntity;
import kr.co.popoolserver.error.model.ResponseFormat;
import kr.co.popoolserver.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping
@Controller
public class AdminS3Controller {

    private final S3Service s3Service;

    private final AdminUserService adminUserService;

    @ApiOperation("S3 Upload UI")
    @GetMapping("/admin/image")
    public String image(){
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        return "s3-image";
    }

    @ApiOperation("이미지 파일 S3 업로드 API")
    @PostMapping("/admin/s3-image")
    @ResponseBody
    public S3Dto.CONVERT uploadS3Image(@RequestParam("data") MultipartFile multipartFile) {
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        return s3Service.uploadS3(multipartFile, "image");
    }

    @ApiOperation("S3 이미지 파일 삭제 API")
    @DeleteMapping("/admin/s3-image")
    @ResponseBody
    public ResponseFormat deleteS3Image(@RequestParam("fileName") String fileName) {
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        s3Service.deleteS3(fileName);
        return ResponseFormat.ok();
    }

    @ApiOperation("S3 이미지 파일 정보 읽기 API")
    @GetMapping("/admin/s3-image/info")
    @ResponseBody
    public ResponseFormat<S3Dto.DOWNLOAD> getS3Info(@RequestParam("fileName") String fileName){
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        return ResponseFormat.ok(s3Service.downloadS3(fileName));
    }

    @ApiOperation("S3 이미지 파일 다운로드 API")
    @GetMapping("/admin/s3-image")
    @ResponseBody
    public ResponseEntity<byte[]> downloadS3Image(@RequestParam("fileName") String fileName){
        final AdminEntity adminEntity = AdminThreadLocal.get();
        adminUserService.checkAdminRole(adminEntity.getUserRole());

        S3Dto.DOWNLOAD download = s3Service.downloadS3(fileName);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }
}