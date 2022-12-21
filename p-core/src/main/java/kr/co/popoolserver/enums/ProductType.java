package kr.co.popoolserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductType {

    COUPON("이용권"),
    SUBSCRIPTION("구독권");

    private String product;

    public static ProductType of(String product){
        return Arrays.stream(ProductType.values())
                .filter(r -> r.toString().equalsIgnoreCase(product))
                .findAny().orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));
    }
}
