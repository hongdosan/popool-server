package kr.co.popoolserver.product.domain.entity;

import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.user.domain.entity.CorporateEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_inventory")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "inventory_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryEntity extends BaseEntity {

    @Column(name = "unused_coupon_amount")
    private int unusedCouponAmount;

    @Column(name = "period_end")
    private LocalDateTime periodEnd;

    @Column(name = "subscription_state")
    private boolean subscriptionState;

    @OneToOne
    @JoinColumn(name = "corporate_id")
    private CorporateEntity corporateEntity;
}
