package kr.co.popoolserver.entity.product;

import kr.co.popoolserver.entity.BaseEntity;
import kr.co.popoolserver.entity.product.dto.ProductDto;
import kr.co.popoolserver.enums.ProductType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {

    @Column(name = "product_name", unique = true, nullable = false, length = 50)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private long productPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @Builder
    public ProductEntity(String productName,
                         long productPrice,
                         String description,
                         ProductType productType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.productType = productType;
    }

    public static ProductEntity of(ProductDto.CREATE create){
        return ProductEntity.builder()
                .productName(create.getProductName())
                .productPrice(create.getProductPrice())
                .description(create.getDescription())
                .productType(ProductType.of(create.getProductType()))
                .build();
    }

    public static List<ProductDto.READ> of(List<ProductEntity> productEntities){
        List<ProductDto.READ> reads = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            ProductDto.READ read = ProductDto.READ.builder()
                    .productName(productEntity.productName)
                    .productType(productEntity.productType)
                    .build();
            reads.add(read);
        }
        return reads;
    }

    public void updateProduct(ProductDto.UPDATE update){
        this.productPrice = update.getProductPrice();
        this.description = update.getDescription();
        this.productType = ProductType.of(update.getProductType());
    }
}
