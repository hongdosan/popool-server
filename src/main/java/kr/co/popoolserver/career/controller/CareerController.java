package kr.co.popoolserver.career.controller;

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

    @PostMapping("/create")
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE create) {
        careerService.createCareer(create);
        return ResponseFormat.ok();
    }

    @GetMapping
    public ResponseFormat<List<CareerDto.READ>> getAllCareers() {
        return ResponseFormat.ok(careerService.getAllCareers());
    }

    @GetMapping("/one")
    public ResponseFormat getCareer(@RequestParam("id") Long id) {
        return ResponseFormat.ok(careerService.getCareer(id));
    }

    @PutMapping
    public ResponseFormat updateCareer() {
        //TODO Update Career
        return ResponseFormat.ok();
    }

    @DeleteMapping
    public ResponseFormat deleteCareer() {
        //TODO Delete Career
        return ResponseFormat.ok();
    }

}
