package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.S3Dto;
import kr.co.popoolserver.career.domain.service.S3Service;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/create/s3/upload")
    public ResponseFormat<String> uploadS3(@RequestParam("image") MultipartFile multipartFile) {
        return ResponseFormat.ok(s3Service.uploadS3(multipartFile));
    }

    @GetMapping("/s3/download")
    public ResponseFormat<S3Dto.DOWNLOAD> download(String fileUrl){
        return ResponseFormat.ok(s3Service.download(fileUrl));
    }

    @PutMapping("/s3")
    public ResponseFormat updateS3() {
        //TODO Update S3
        return ResponseFormat.ok();
    }

    @DeleteMapping("/s3")
    public ResponseFormat deleteS3(String fileS3Path) {
        s3Service.removeS3(fileS3Path);
        return ResponseFormat.ok();
    }
}
