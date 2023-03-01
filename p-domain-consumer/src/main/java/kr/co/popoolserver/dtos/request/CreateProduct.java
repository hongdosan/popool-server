package kr.co.popoolserver.dtos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateProduct {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CREATE_PRODUCT{
        @ApiModelProperty(example = "구독권")
        @NotBlank(message = "상품 이름을 입력하세요.")
        private String productName;

        @ApiModelProperty(example = "1000")
        @NotNull(message = "상품 가격을 입력하세요.")
        private long productPrice;

        @ApiModelProperty(example = "구독권입니다.")
        @NotBlank(message = "상품 설명을 입력하세요.")
        private String description;
    }
}
