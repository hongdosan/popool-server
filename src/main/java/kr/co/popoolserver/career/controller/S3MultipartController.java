package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.service.S3MultipartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class S3MultipartController {

    private final S3MultipartService s3MultipartService;
}
