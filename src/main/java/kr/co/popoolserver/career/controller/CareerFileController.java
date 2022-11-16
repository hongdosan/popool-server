package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import kr.co.popoolserver.common.infra.s3.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers")
public class CareerFileController {

    private final S3Upload s3Upload;

    @PostMapping("/upload")
    public ResponseFormat<String> uploadImage(@RequestParam("image") MultipartFile multipartFile) {
        return ResponseFormat.ok(s3Upload.uploadFile(multipartFile));
    }
}
