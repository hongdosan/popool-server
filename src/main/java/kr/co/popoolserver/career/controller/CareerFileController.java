package kr.co.popoolserver.career.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.service.CareerFileService;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
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

    @ApiOperation("CareerFile 수정")
    @PutMapping
    public ResponseFormat updateCareerFile(){
        //TODO Update Career File;
        return ResponseFormat.ok();
    }

    @ApiOperation("CareerFile Info 읽기")
    @GetMapping("/info")
    public ResponseFormat<CareerFileDto.READ_INFO> getCareerFileInfo() {
        //TODO IMAGE INFO READ
        return ResponseFormat.ok(careerFileService.getCareerFileInfo());
    }

    @ApiOperation("CareerFile Image 읽기")
    @GetMapping
    public ResponseFormat getCareerFile() {
        //TODO IMAGE READ
        return ResponseFormat.ok();
    }

    @ApiOperation("CareerFile 삭제")
    @DeleteMapping
    public ResponseFormat deleteCareerFile(){
        //TODO Delete Career File;
        return ResponseFormat.ok();
    }
}
