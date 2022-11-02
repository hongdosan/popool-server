package kr.co.popoolserver.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductType {

    PRODUCT_COUPON("이용권"),
    PRODUCT_PERIOD_COUPON("기간권"),
    PRODUCT_SUBSCRIPTION("구독권");

    private String product;

    public static ProductType of(String product){
        return Arrays.stream(ProductType.values())
                .filter(r -> r.toString().equalsIgnoreCase(product))
                .findAny().orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));
    }
}
