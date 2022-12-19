package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminProductController {

    @ApiOperation("상품 등록 / 권한 : 관리자")
    @PostMapping("/payments")
    public ResponseFormat createProduct(){
        ///TODO Create Products
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 수정 / 권한 : 관리자")
    @PutMapping("/payments")
    public ResponseFormat updateProduct(){
        ///TODO Update Product
        return ResponseFormat.ok();
    }
}
