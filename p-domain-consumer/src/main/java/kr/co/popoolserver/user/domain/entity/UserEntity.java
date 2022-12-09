package kr.co.popoolserver.user.domain.entity;

import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.domain.entity.model.Address;
import kr.co.popoolserver.infrastructure.shared.BaseEntity;
import kr.co.popoolserver.user.domain.entity.model.PhoneNumber;
import kr.co.popoolserver.enums.Gender;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Column(name = "identity", unique = true, nullable = false, length = 25)
    private String identity;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "birth", nullable = false, length = 8)
    private String birth;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Embedded
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number", unique = true))
    private PhoneNumber phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode")),
            @AttributeOverride(name = "address1", column = @Column(name = "address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "address2"))
    })
    private Address address;

    @Builder
    public UserEntity(String identity,
                      String password,
                      String name,
                      String birth,
                      Gender gender,
                      PhoneNumber phoneNumber,
                      UserRole userRole) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public static UserEntity of(UserDto.CREATE create, PasswordEncoder passwordEncoder){
        return UserEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .phoneNumber(new PhoneNumber(create.getPhone()))
                .birth(create.getBirth())
                .gender(Gender.of(create.getGender()))
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    public static UserDto.READ of(UserEntity userEntity){
        return UserDto.READ.builder()
                .name(userEntity.getName())
                .birth(userEntity.getBirth())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .userRole(userEntity.getUserRole())
                .createAt(userEntity.getCreatedAt())
                .build();
    }

    public void updateInfo(UserDto.UPDATE update){
        this.name = update.getName();
        this.birth = update.getBirth();
        this.gender = Gender.of(update.getGender());
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateEmail(String email){
        this.email = email;
    }

    public void updatePhone(PhoneNumber phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void updateAddress(UserCommonDto.UPDATE_ADDRESS address){
        this.address = Address.builder()
                .zipcode(address.getZipCode())
                .address1(address.getAddr1())
                .address2(address.getAddr2())
                .build();
    }
}
