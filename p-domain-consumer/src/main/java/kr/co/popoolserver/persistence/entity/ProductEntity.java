package kr.co.popoolserver.persistence.entity;

import kr.co.popoolserver.dtos.request.CreateProduct;
import kr.co.popoolserver.dtos.request.UpdateProduct;
import kr.co.popoolserver.dtos.response.ResponseProduct;

import kr.co.popoolserver.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_product")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {

    @Column(name = "product_name", unique = true, nullable = false)
    @NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    private String productName;

    @Column(name = "product_price", nullable = false)
    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private long productPrice;

    @Column(name = "description")
    private String description;

    @Builder
    public ProductEntity(String productName,
                         long productPrice,
                         String description) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
    }

    public static ProductEntity of(CreateProduct.CREATE_PRODUCT createProduct){
        return ProductEntity.builder()
                .productName(createProduct.getProductName())
                .productPrice(createProduct.getProductPrice())
                .description(createProduct.getDescription())
                .build();
    }

    public static ResponseProduct.READ_PRODUCT_DETAIL toDetailDto(ProductEntity productEntity){
        return ResponseProduct.READ_PRODUCT_DETAIL.builder()
                .productName(productEntity.productName)
                .productPrice(productEntity.productPrice)
                .description(productEntity.description)
                .build();
    }

    public static ResponseProduct.READ_PRODUCT toDto(ProductEntity productEntity){
        return ResponseProduct.READ_PRODUCT.builder()
                .productName(productEntity.productName)
                .build();
    }

    public void updateProduct(UpdateProduct.UPDATE_PRODUCT updateProduct){
        this.productPrice = updateProduct.getProductPrice();
        this.description = updateProduct.getDescription();
    }
}
