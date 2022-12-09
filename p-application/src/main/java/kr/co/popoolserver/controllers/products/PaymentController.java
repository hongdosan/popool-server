package kr.co.popoolserver.controllers.products;

import io.swagger.annotations.ApiOperation;
import kr.co.popoolserver.error.model.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @ApiOperation("상품 결제 / 권한 : 일반 회원 이상")
    @PostMapping
    public ResponseFormat payment(){
        ///TODO payment product API
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 모든 결제 기록 조회 / 권한 : 일반 회원 이상")
    @GetMapping
    public ResponseFormat getAllPayments(){
        ///TODO Get All Payments API
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 결제 기록 세부 조회 / 권한 : 일반 회원 이상")
    @GetMapping("/detail")
    public ResponseFormat getPayment(){
        ///TODO Get Payment API
        return ResponseFormat.ok();
    }
}
