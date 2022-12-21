package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.admin.service.AdminProductService;
import kr.co.popoolserver.entity.product.dto.ProductDto;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @ApiOperation("상품 등록 / 권한 : 관리자")
    @PostMapping("/products")
    public ResponseFormat createProduct(@RequestBody @Valid ProductDto.CREATE create){
        adminProductService.createProduct(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 수정 / 권한 : 관리자")
    @PutMapping("/products")
    public ResponseFormat updateProduct(){
        ///TODO Update Product
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 삭제 / 권한 : 관리자")
    @DeleteMapping("/products")
    public ResponseFormat deleteProduct(){
        ///TODO Delete Product
        return ResponseFormat.ok();
    }
}
