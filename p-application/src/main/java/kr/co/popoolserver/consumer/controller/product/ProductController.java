package kr.co.popoolserver.consumer.controller.product;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.consumer.service.product.ProductService;
import kr.co.popoolserver.entity.product.dto.ProductDto;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @ApiOperation("모든 상품 조회 / 권한 : 일반 회원 이상")
    @GetMapping
    public ResponseFormat<List<ProductDto.READ>> getAllProducts(){
        return ResponseFormat.ok(productService.getProducts());
    }

    @ApiOperation("상품 세부사항 조회 / 권한 : 일반 회원 이상")
    @GetMapping("/detail")
    public ResponseFormat<ProductDto.READ_DETAIL> getProduct(@RequestParam("product_name") String productName){
        ///TODO get detail Product
        return ResponseFormat.ok();
    }
}
