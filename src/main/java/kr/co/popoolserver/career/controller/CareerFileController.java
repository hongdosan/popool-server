package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.service.CareerFileService;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers/files")
public class CareerFileController {

    private final CareerFileService careerFileService;

    @GetMapping("/info")
    public ResponseFormat<CareerFileDto.READ_INFO> getCareerFileInfo() {
        return ResponseFormat.ok(careerFileService.getCareerFileInfo());
    }

    @GetMapping
    public ResponseFormat getCareerFile() {
        //TODO IMAGE READ
        return ResponseFormat.ok();
    }

}
