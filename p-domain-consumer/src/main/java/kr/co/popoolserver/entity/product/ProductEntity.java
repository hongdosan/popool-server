package kr.co.popoolserver.entity.product;

import kr.co.popoolserver.entity.BaseEntity;
import kr.co.popoolserver.enums.ProductType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_product")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private long productPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;
}
