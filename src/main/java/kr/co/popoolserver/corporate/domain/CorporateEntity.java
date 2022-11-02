package kr.co.popoolserver.corporate.domain;

import kr.co.popoolserver.common.domain.Address;
import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.common.domain.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_corporate")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "corporate_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporateEntity extends BaseEntity {

    @Column(name = "business_number", nullable = false, length = 100)
    private String businessNumber;

    @Column(name = "business_name", nullable = false, length = 100)
    private String businessName;

    @Column(name = "business_ceo_name", nullable = false, length = 100)
    private String businessCeoName;

    @Column(name = "business_phone_number", nullable = false, length = 100)
    private String businessPhoneNumber;

    @Column(name = "business_email", nullable = false, length = 100)
    private String businessEmail;

    @Column(name = "identity", unique = true, nullable = false, length = 25)
    private String identity;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode")),
            @AttributeOverride(name = "address1", column = @Column(name = "address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "address2"))
    })
    private Address businessAddress;

    @Builder
    public CorporateEntity(String businessNumber,
                           String businessName,
                           String businessCeoName,
                           String businessPhoneNumber,
                           String businessEmail,
                           String identity,
                           String password,
                           String name,
                           Address businessAddress) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessCeoName = businessCeoName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessEmail = businessEmail;
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.businessAddress = businessAddress;
    }
}
