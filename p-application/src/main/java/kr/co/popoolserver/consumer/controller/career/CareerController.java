package kr.co.popoolserver.consumer.controller.career;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.entity.career.dto.CareerDto;
import kr.co.popoolserver.consumer.service.career.CareerService;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("이력서 생성 / 권한 : 일반 회원 이상")
    @PostMapping
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE create) {
        careerService.createCareer(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("나의 모든 이력서 조회 / 권한 : 일반 회원 이상")
    @GetMapping
    public ResponseFormat<List<CareerDto.READ>> getAllCareers() {
        return ResponseFormat.ok(careerService.getAllCareers());
    }

    @ApiOperation("나의 이력서 세부 조회  / 권한 : 일반 회원 이상")
    @GetMapping("/detail")
    public ResponseFormat getCareer(@RequestParam("career_id") Long careerId) {
        return ResponseFormat.ok(careerService.getCareer(careerId));
    }

    @ApiOperation("다른 사람의 이력서 세부 조회 / 권한 : 기업 회원 이상")
    @GetMapping("/others")
    public ResponseFormat getOthersCareer(@RequestParam("career_id") Long careerId) {
        return ResponseFormat.ok(careerService.getOthersCareer(careerId));
    }

    @ApiOperation("본인 이력서 수정 / 권한 : 일반 회원 이상 ")
    @PutMapping
    public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE update) {
        careerService.updateCareer(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 이력서 삭제 / 권한 : 일반 회원 이상")
    @DeleteMapping
    public ResponseFormat deleteCareer(@RequestParam("career_id") Long careerId) {
        careerService.deleteCareer(careerId);
        return ResponseFormat.ok();
    }
}
