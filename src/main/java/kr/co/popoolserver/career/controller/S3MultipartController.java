package kr.co.popoolserver.career.controller;

import kr.co.popoolserver.career.domain.dto.S3MultipartDto;
import kr.co.popoolserver.career.domain.service.S3MultipartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class S3MultipartController {

    private final S3MultipartService s3MultipartService;

    @PostMapping("/init-upload")
    public S3MultipartDto.UPLOAD initUpload(@RequestParam("fileName") String fileName){
        return s3MultipartService.initUpload(fileName);
    }

    @PostMapping("/signed-url")
    public String getUploadSignedUrl(@RequestBody S3MultipartDto.UPLOAD_SIGN_URL uploadSignUrl){
        return s3MultipartService.getUploadSignedUrl(uploadSignUrl);
    }

    @PostMapping("/complete-upload")
    public S3MultipartDto.UPLOAD_RESULT completeUpload(@RequestBody S3MultipartDto.COMPLETED_UPLOAD completedUpload){
        return s3MultipartService.completeUpload(completedUpload);
    }
}
