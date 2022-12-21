package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.admin.service.AdminService;
import kr.co.popoolserver.dto.S3Dto;
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
    private final AdminService adminService;

    @ApiOperation("S3 Upload UI")
    @GetMapping("/admin/image")
    public String image(){
        adminService.checkAdmin();
        return "s3-image";
    }

    @ApiOperation("S3 Upload API /이미지 파일 업로드")
    @PostMapping("/admin/s3-image")
    @ResponseBody
    public S3Dto.CONVERT uploadS3Image(@RequestParam("data") MultipartFile multipartFile) {
        adminService.checkAdmin();
        return s3Service.uploadS3(multipartFile, "image");
    }

    @ApiOperation("S3 Delete API /이미지 파일 삭제")
    @DeleteMapping("/admin/s3-image")
    @ResponseBody
    public ResponseFormat deleteS3Image(@RequestParam("fileName") String fileName) {
        adminService.checkAdmin();
        s3Service.deleteS3(fileName);
        return ResponseFormat.ok();
    }

    @ApiOperation("S3 File Info Read API/이미지 파일 정보 읽기")
    @GetMapping("/admin/s3-image/info")
    @ResponseBody
    public ResponseFormat<S3Dto.DOWNLOAD> downloadInfo(@RequestParam("fileName") String fileName){
        adminService.checkAdmin();
        return ResponseFormat.ok(s3Service.downloadS3(fileName));
    }

    @ApiOperation("S3 File Download API/이미지 파일 다운로드")
    @GetMapping("/admin/s3-image")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName){
        adminService.checkAdmin();
        S3Dto.DOWNLOAD download = s3Service.downloadS3(fileName);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }
}
