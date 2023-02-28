package kr.co.popoolserver.entity.user;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.model.Address;
import kr.co.popoolserver.entity.BaseEntity;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import kr.co.popoolserver.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_corporate")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "corporate_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorporateEntity extends BaseEntity {

    @Column(name = "business_number", nullable = false)
    @NotBlank(message = "사업자 번호는 필수 입력 값입니다.")
    private String businessNumber;

    @Column(name = "business_name", nullable = false)
    @NotBlank(message = "사업자명은 필수 입력 값입니다.")
    private String businessName;

    @Column(name = "business_ceo_name", nullable = false)
    @NotBlank(message = "대표명은 필수 입력 값입니다.")
    private String businessCeoName;

    @Column(name = "business_email", nullable = false)
    @NotBlank(message = "회사 메일은 필수 입력 값입니다.")
    private String businessEmail;

    @Column(name = "identity", unique = true, nullable = false)
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String identity;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Embedded
    @NotNull(message = "회사 전화번호는 필수 입력 값입니다.")
    @AttributeOverride(
            name = "phoneNumber",
            column = @Column(name = "business_phone_number")
    )
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

    public static CorporateEntity of(CreateUsers.CREATE_CORPORATE createCorporate,
                                     PasswordEncoder passwordEncoder){
        return CorporateEntity.builder()
                .identity(createCorporate.getIdentity())
                .password(passwordEncoder.encode(createCorporate.getPassword()))
                .businessName(createCorporate.getBusinessName())
                .businessNumber(createCorporate.getBusinessNumber())
                .businessCeoName(createCorporate.getBusinessCeoName())
                .businessEmail(createCorporate.getBusinessEmail())
                .name(createCorporate.getName())
                .userRole(UserRole.ROLE_CORPORATE)
                .businessPhoneNumber(PhoneNumber.builder()
                        .phoneNumber(createCorporate.getBusinessPhoneNumber())
                        .build())
                .businessAddress(Address.builder()
                        .zipcode(createCorporate.getZipCode())
                        .address1(createCorporate.getAddr1())
                        .address2(createCorporate.getAddr2())
                        .build())
                .build();
    }

    public static ResponseUsers.READ_CORPORATE of(CorporateEntity corporateEntity){
        return ResponseUsers.READ_CORPORATE.builder()
                .name(corporateEntity.name)
                .businessName(corporateEntity.businessName)
                .businessCeoName(corporateEntity.getBusinessCeoName())
                .businessNumber(corporateEntity.getBusinessNumber())
                .userRole(corporateEntity.userRole)
                .createAt(corporateEntity.createdAt)
                .build();
    }

    public void updateInfo(UpdateUsers.UPDATE_CORPORATE updateCorporate){
        this.name = updateCorporate.getName();
        this.businessNumber = updateCorporate.getBusinessNumber();
        this.businessName = updateCorporate.getBusinessName();
        this.businessCeoName = updateCorporate.getBusinessCeoName();
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

    public void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress){
        this.businessAddress = Address.builder()
                .zipcode(updateAddress.getZipCode())
                .address1(updateAddress.getAddr1())
                .address2(updateAddress.getAddr2())
                .build();
    }
}
