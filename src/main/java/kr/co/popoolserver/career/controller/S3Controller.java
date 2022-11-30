package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.S3Dto;
import kr.co.popoolserver.career.domain.service.S3Service;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping
@Controller
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/image")
    public String image(){
        return "s3-image";
    }

    @PostMapping("/s3-image")
    @ResponseBody
    public String uploadS3Image(@RequestParam("data") MultipartFile multipartFile) {
        return s3Service.uploadS3(multipartFile, "image");
    }

    @DeleteMapping("/s3-image")
    @ResponseBody
    public ResponseFormat deleteS3Image(@RequestParam("fileName") String fileName) {
        s3Service.deleteS3(fileName);
        return ResponseFormat.ok();
    }

    @GetMapping("/s3-image/info")
    @ResponseBody
    public ResponseFormat<S3Dto.DOWNLOAD> downloadInfo(@RequestParam("fileName") String fileName){
        return ResponseFormat.ok(s3Service.downloadS3(fileName));
    }

    @GetMapping("/s3-image")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName){
        S3Dto.DOWNLOAD download = s3Service.downloadS3(fileName);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }
}
