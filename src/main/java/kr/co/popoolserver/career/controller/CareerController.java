package kr.co.popoolserver.career.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.career.domain.dto.CareerDto;
import kr.co.popoolserver.career.domain.service.CareerService;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("이력서 생성")
    @PostMapping
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE create) {
        careerService.createCareer(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("나의 모든 이력서 조회")
    @GetMapping
    public ResponseFormat<List<CareerDto.READ>> getAllCareers() {
        return ResponseFormat.ok(careerService.getAllCareers());
    }

    @ApiOperation("나의 이력서 1개 조회")
    @GetMapping("/one")
    public ResponseFormat getCareer(@RequestParam("career_id") Long careerId) {
        return ResponseFormat.ok(careerService.getCareer(careerId));
    }

    @ApiOperation("이력서 수정")
    @PutMapping
    public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE update) {
        careerService.updateCareer(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("이력서 삭제")
    @DeleteMapping
    public ResponseFormat deleteCareer(@RequestParam("career_id") Long careerId) {
        careerService.deleteCareer(careerId);
        return ResponseFormat.ok();
    }
}
