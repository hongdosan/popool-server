package kr.co.popoolserver.product.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    @ApiOperation("상품 추가")
    @PostMapping
    public ResponseFormat createProduct(){
        //TODO create Product API
        return ResponseFormat.ok();
    }

    @ApiOperation("모든 상품 조회")
    @GetMapping
    public ResponseFormat getAllProduct(){
        ///TODO get all Product
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 세부사항 조회")
    @GetMapping("/detail")
    public ResponseFormat getProduct(){
        ///TODO get detail Product
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 삭제")
    @DeleteMapping
    public ResponseFormat deleteProduct(){
        ///TODO delete Product
        return ResponseFormat.ok();
    }
}
