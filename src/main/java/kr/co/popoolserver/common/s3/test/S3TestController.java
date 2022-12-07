package kr.co.popoolserver.common.s3.test;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.s3.S3Dto;
import kr.co.popoolserver.common.s3.S3Service;
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
public class S3TestController {

    private final S3Service s3Service;

    @ApiOperation("S3 Upload UI")
    @GetMapping("/image")
    public String image(){
        return "s3-image";
    }

    @ApiOperation("S3 Upload API /이미지 파일 업로드")
    @PostMapping("/s3-image")
    @ResponseBody
    public String uploadS3Image(@RequestParam("data") MultipartFile multipartFile) {
        return s3Service.uploadS3(multipartFile, "image");
    }

    @ApiOperation("S3 Delete API /이미지 파일 삭제")
    @DeleteMapping("/s3-image")
    @ResponseBody
    public ResponseFormat deleteS3Image(@RequestParam("fileName") String fileName) {
        s3Service.deleteS3(fileName);
        return ResponseFormat.ok();
    }

    @ApiOperation("S3 File Info Read API/이미지 파일 정보 읽기")
    @GetMapping("/s3-image/info")
    @ResponseBody
    public ResponseFormat<S3Dto.DOWNLOAD> downloadInfo(@RequestParam("fileName") String fileName){
        return ResponseFormat.ok(s3Service.downloadS3(fileName));
    }

    @ApiOperation("S3 File Download API/이미지 파일 다운로드")
    @GetMapping("/s3-image")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName){
        S3Dto.DOWNLOAD download = s3Service.downloadS3(fileName);
        return new ResponseEntity<>(download.getBytes(), download.getHttpHeaders(), HttpStatus.OK);
    }
}
