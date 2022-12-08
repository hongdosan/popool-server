package kr.co.popoolserver.career.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.service.CareerFileService;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers/files")
public class CareerFileController {

    private final CareerFileService careerFileService;

    @ApiOperation("프로필 이미지 생성 및 변경 / S3, DB 모두 저장됨 / 권한 : 일반 회원 이상")
    @PostMapping
    public ResponseFormat createCareerFile(@RequestParam("data") MultipartFile multipartFile){
        careerFileService.createCareerFile(multipartFile);
        return ResponseFormat.ok();
    }

    @ApiOperation("프로필 이미지 다운로드 / 권한 : 일반 회원 이상")
    @GetMapping
    public ResponseEntity<byte[]> getCareerFile() {
        CareerFileDto.DOWNLOAD download = careerFileService.getCareerFileDownload();
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation("프로필 이미지 삭제 / S3, DB 모두 삭제됨 / 권한 : 일반 회원 이상")
    @DeleteMapping
    public ResponseFormat deleteCareerFile(){
        careerFileService.deleteCareerFile();
        return ResponseFormat.ok();
    }
}
