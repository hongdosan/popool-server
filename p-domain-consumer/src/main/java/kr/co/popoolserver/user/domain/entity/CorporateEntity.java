package kr.co.popoolserver.user.domain.entity;

import kr.co.popoolserver.user.domain.entity.model.Address;
import kr.co.popoolserver.infrastructure.shared.BaseEntity;
import kr.co.popoolserver.user.domain.entity.model.PhoneNumber;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.user.domain.dto.CorporateDto;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "business_phone_number", unique = true))
    private PhoneNumber businessPhoneNumber;

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
                           PhoneNumber businessPhoneNumber,
                           Address businessAddress,
                           String businessEmail,
                           String identity,
                           String password,
                           String name,
                           UserRole userRole) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessCeoName = businessCeoName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessAddress = businessAddress;
        this.businessEmail = businessEmail;
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }

    public static CorporateEntity of(CorporateDto.CREATE create,
                                     PasswordEncoder passwordEncoder){
        return CorporateEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .businessName(create.getBusinessName())
                .businessNumber(create.getBusinessNumber())
                .businessCeoName(create.getBusinessCeoName())
                .businessEmail(create.getBusinessEmail())
                .businessPhoneNumber(new PhoneNumber(create.getBusinessPhoneNumber()))
                .businessAddress(new Address(create.getZipCode(), create.getAddr1(), create.getAddr2()))
                .name(create.getName())
                .userRole(UserRole.ROLE_CORPORATE)
                .build();
    }

    public static CorporateDto.READ of(CorporateEntity corporateEntity){
        return CorporateDto.READ.builder()
                .name(corporateEntity.name)
                .businessName(corporateEntity.businessName)
                .businessCeoName(corporateEntity.getBusinessCeoName())
                .businessNumber(corporateEntity.getBusinessNumber())
                .userRole(corporateEntity.userRole)
                .createAt(corporateEntity.createdAt)
                .build();
    }

    public void updateInfo(CorporateDto.UPDATE update){
        this.name = update.getName();
        this.businessNumber = update.getBusinessNumber();
        this.businessName = update.getBusinessName();
        this.businessCeoName = update.getBusinessCeoName();
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateEmail(String email){
        this.businessEmail = email;
    }

    public void updatePhone(PhoneNumber phoneNumber){
        this.businessPhoneNumber = phoneNumber;
    }

    public void updateAddress(UserCommonDto.UPDATE_ADDRESS address){
        this.businessAddress = Address.builder()
                .zipcode(address.getZipCode())
                .address1(address.getAddr1())
                .address2(address.getAddr2())
                .build();
    }
}
