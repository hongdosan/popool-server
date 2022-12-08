package kr.co.popoolserver.career.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.service.CareerFileService;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
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

    @ApiOperation("S3 Image File 저장 & DB Image Meta Data 저장 Service")
    @PostMapping
    public ResponseFormat createCareerFile(@RequestParam("data") MultipartFile multipartFile){
        careerFileService.createCareerFile(multipartFile);
        return ResponseFormat.ok();
    }

    @ApiOperation("Career File Meta Data 읽기")
    @GetMapping("/info")
    public ResponseFormat<CareerFileDto.READ_INFO> getCareerFileInfo() {
        return ResponseFormat.ok(careerFileService.getCareerFileInfo());
    }

    @ApiOperation("Career File S3 Image 다운로드")
    @GetMapping
    public ResponseEntity<byte[]> getCareerFile() {
        CareerFileDto.DOWNLOAD download = careerFileService.getCareerFileDownload();
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation("S3 Image File 삭제 & DB Image Meta Data 삭제 Service")
    @DeleteMapping
    public ResponseFormat deleteCareerFile(){
        careerFileService.deleteCareerFile();
        return ResponseFormat.ok();
    }
}
