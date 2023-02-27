package kr.co.popoolserver.consumer.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.consumer.service.career.CareerService;
import kr.co.popoolserver.dtos.S3Dto;
import kr.co.popoolserver.dtos.request.CreateCareers;
import kr.co.popoolserver.dtos.request.UpdateCareers;
import kr.co.popoolserver.dtos.response.ResponseCareers;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userType}/careers")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("이력서 생성 API")
    @PostMapping
    public ResponseFormat<String> createCareer(@PathVariable(name = "userType") UserType userType,
                                               @RequestBody CreateCareers.CREATE_CAREER createCareer) {
        careerService.createCareer(userType, createCareer);
        return ResponseFormat.ok("이력서 등록 완료");
    }

    @ApiOperation("프로필 이미지 생성 및 변경 API ")
    @PostMapping("/image")
    public ResponseFormat<String> createCareerFile(@PathVariable(name = "userType") UserType userType,
                                                   @RequestParam("data") MultipartFile multipartFile){
        careerService.createCareerFile(userType, multipartFile);
        return ResponseFormat.ok("프로필 등록 완료");
    }

    @ApiOperation("본인 모든 이력서 조회 API")
    @GetMapping
    public ResponseFormat<List<ResponseCareers.READ_CAREER>> getAllCareers(@PathVariable(name = "userType") UserType userType) {
        return ResponseFormat.ok(careerService.getAllCareers(userType));
    }

    @ApiOperation("본인 이력서 세부 조회 API")
    @GetMapping("/detail")
    public ResponseFormat<ResponseCareers.READ_CAREER> getCareer(@PathVariable(name = "userType") UserType userType,
                                                                 @RequestParam("careerId") Long careerId) {
        return ResponseFormat.ok(careerService.getCareer(userType, careerId));
    }

    @ApiOperation("다른 사람 이력서 세부 조회 API")
    @GetMapping("/others")
    public ResponseFormat<ResponseCareers.READ_CAREER> getOthersCareer(@PathVariable(name = "userType") UserType userType,
                                                                       @RequestParam("careerId") Long careerId) {
        return ResponseFormat.ok(careerService.getOthersCareer(userType, careerId));
    }

    @ApiOperation("프로필 이미지 다운로드 API")
    @GetMapping("/image")
    public ResponseEntity<byte[]> getCareerFile(@PathVariable(name = "userType") UserType userType) {
        S3Dto.DOWNLOAD download = careerService.getCareerFileDownload(userType);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation("본인 이력서 수정 API")
    @PutMapping
    public ResponseFormat<String> updateCareer(@PathVariable(name = "userType") UserType userType,
                                               @RequestBody UpdateCareers.UPDATE_CAREER updateCareer) {
        careerService.updateCareer(userType, updateCareer);
        return ResponseFormat.ok("이력서 수정 완료");
    }

    @ApiOperation("본인 이력서 삭제 API")
    @DeleteMapping
    public ResponseFormat<String> deleteCareer(@PathVariable(name = "userType") UserType userType,
                                               @RequestParam("careerId") Long careerId) {
        careerService.deleteCareer(userType, careerId);
        return ResponseFormat.ok("이력서 삭제 완료");
    }

    @ApiOperation("프로필 이미지 삭제 API")
    @DeleteMapping("/image")
    public ResponseFormat<String> deleteCareerFile(@PathVariable(name = "userType") UserType userType){
        careerService.deleteCareerFile(userType);
        return ResponseFormat.ok("프로필 이미지 삭제 완료");
    }
}
