package kr.co.popoolserver.product.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @ApiOperation("인벤토리 상품 추가 / 권한 : 일반 회원 이상")
    @PostMapping
    public ResponseFormat addInventory(){
        ///TODO My Inventory Add Product  API
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 인벤토리 모두 조회 / 권한 : 일반 회원 이상")
    @GetMapping
    public ResponseFormat getAllInventories(){
        ///TODO Get All Inventories API
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 인벤토리 상품 세부 조회 / 권한 : 일반 회원 이상")
    @GetMapping("/detail")
    public ResponseFormat getInventory(){
        ///TODO Get Inventory API
        return ResponseFormat.ok();
    }

    @ApiOperation("인벤토리 상품 수정 / 권한 : 일반 회원 이상")
    @PutMapping
    public ResponseFormat updateInventory(){
        ///TODO My Inventory Update Product  API
        return ResponseFormat.ok();
    }

    @ApiOperation("인벤토리 상품 제거 / 권한 : 일반 회원 이상")
    @DeleteMapping
    public ResponseFormat deleteInventory(){
        ///TODO My Inventory Delete Product  API
        return ResponseFormat.ok();
    }
}
