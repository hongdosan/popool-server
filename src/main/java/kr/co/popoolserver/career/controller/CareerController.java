package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.CareerDto;
import kr.co.popoolserver.career.domain.service.CareerService;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ResponseFormat<CareerDto.READ> getCareer() {
        return ResponseFormat.ok(careerService.getCareer());
    }

}
