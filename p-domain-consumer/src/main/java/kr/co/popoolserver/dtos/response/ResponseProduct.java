package kr.co.popoolserver.dtos.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ResponseProduct {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_PRODUCT{
        @ApiModelProperty(example = "구독권")
        private String productName;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_PRODUCT_DETAIL{
        @ApiModelProperty(example = "구독권")
        private String productName;

        @ApiModelProperty(example = "1000")
        private long productPrice;

        @ApiModelProperty(example = "구독권입니다.")
        private String description;
    }
}
