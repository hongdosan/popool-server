package kr.co.popoolserver.product.domain.entity;

import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.common.domain.enums.ProductType;
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

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;
}
