package kr.co.popoolserver.entity.product.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popoolserver.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CREATE{
        @ApiModelProperty(example = "구독권")
        @NotBlank(message = "상품 이름을 입력하세요.")
        private String productName;

        @ApiModelProperty(example = "1000")
        @NotNull(message = "상품 가격을 입력하세요.")
        private long productPrice;

        @ApiModelProperty(example = "구독권입니다.")
        @NotBlank(message = "상품 설명을 입력하세요.")
        private String description;

        @ApiModelProperty(example = "SUBSCRIPTION")
        @NotBlank(message = "상품 종류를 입력해주세요.(COUPON, SUBSCRIPTION")
        private String productType;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UPDATE{
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;

        @ApiModelProperty(example = "구독권")
        @NotBlank(message = "상품 이름")
        private String originalProductName;

        @ApiModelProperty(example = "1000")
        @NotNull(message = "상품 가격을 입력하세요.")
        private long productPrice;

        @ApiModelProperty(example = "구독권입니다.")
        @NotBlank(message = "상품 설명을 입력하세요.")
        private String description;

        @ApiModelProperty(example = "SUBSCRIPTION")
        @NotBlank(message = "상품 종류를 입력해주세요.(COUPON, SUBSCRIPTION")
        private String productType;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class DELETE{
        @ApiModelProperty(example = "구독권")
        @NotBlank(message = "상품 이름을 입력하세요.")
        private String productName;

        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ{
        @ApiModelProperty(example = "구독권")
        private String productName;

        @ApiModelProperty(example = "SUBSCRIPTION")
        private ProductType productType;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class READ_DETAIL{
        @ApiModelProperty(example = "구독권")
        private String productName;

        @ApiModelProperty(example = "1000")
        private long productPrice;

        @ApiModelProperty(example = "구독권입니다.")
        private String description;

        @ApiModelProperty(example = "SUBSCRIPTION")
        private ProductType productType;
    }
}
