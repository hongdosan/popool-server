package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.S3Dto;
import kr.co.popoolserver.career.domain.service.S3Service;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/careers")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/s3/file")
    public ResponseFormat<String> uploadS3File(@RequestParam("image") MultipartFile multipartFile) {
        return ResponseFormat.ok(s3Service.uploadS3(multipartFile));
    }

    @DeleteMapping("/s3/file")
    public ResponseFormat deleteS3File(@RequestParam("fileName") String fileName) {
        s3Service.deleteS3(fileName);
        return ResponseFormat.ok();
    }

    @GetMapping("/s3/file/info/{fileName}")
    public ResponseFormat<S3Dto.DOWNLOAD> getFileInfo(@PathVariable String fileName){
        return ResponseFormat.ok(s3Service.downloadS3(fileName));
    }

    @GetMapping("/s3/file/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName){
        S3Dto.DOWNLOAD download = s3Service.downloadS3(fileName);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }
}
