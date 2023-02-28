package kr.co.popoolserver.admin.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.admin.service.AdminProductService;
import kr.co.popoolserver.dtos.request.CreateProduct;
import kr.co.popoolserver.dtos.request.UpdateProduct;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @ApiOperation("상품 등록 API")
    @PostMapping
    public ResponseFormat<String> createProduct(@RequestBody @Valid CreateProduct.CREATE_PRODUCT createProduct){
        adminProductService.createProduct(createProduct);
        return ResponseFormat.ok(createProduct.getProductName() + " 상품 등록 완료");
    }

    @ApiOperation("상품 수정 API")
    @PutMapping
    public ResponseFormat<String> updateProduct(@RequestBody @Valid UpdateProduct.UPDATE_PRODUCT updateProduct){
        adminProductService.updateProduct(updateProduct);
        return ResponseFormat.ok(updateProduct.getOriginalProductName() + " 상품 변경 완료");
    }

    @ApiOperation("상품 삭제 API")
    @DeleteMapping
    public ResponseFormat<String> deleteProduct(@RequestBody @Valid UpdateProduct.DELETE_PRODUCT deleteProduct){
        adminProductService.deleteProduct(deleteProduct);
        return ResponseFormat.ok(deleteProduct.getProductName() + " 상품 삭제 완료");
    }
}