package kr.co.popoolserver.persistence.entity;

import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.enums.Gender;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.persistence.BaseEntity;
import kr.co.popoolserver.persistence.entity.model.Address;
import kr.co.popoolserver.persistence.entity.model.PhoneNumber;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_users")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "users_id"))
@Where(clause = "is_deleted = 0")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Column(name = "identity", unique = true, nullable = false)
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String identity;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Column(name = "birth", nullable = false)
    @NotBlank(message = "생년월일 필수 입력 값입니다.")
    private String birth;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Embedded
    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    @AttributeOverride(
            name = "phoneNumber",
            column = @Column(name = "phone_number", unique = true, nullable = false)
    )
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
                      String email,
                      PhoneNumber phoneNumber,
                      UserRole userRole) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public static UserEntity of(CreateUsers.CREATE_USER createUser,
                                PasswordEncoder passwordEncoder){
        return UserEntity.builder()
                .identity(createUser.getIdentity())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .name(createUser.getName())
                .phoneNumber(new PhoneNumber(createUser.getPhoneNumber()))
                .email(createUser.getEmail())
                .birth(createUser.getBirth())
                .gender(Gender.of(createUser.getGender()))
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    public static ResponseUsers.READ_USER of(UserEntity userEntity){
        return ResponseUsers.READ_USER.builder()
                .name(userEntity.getName())
                .birth(userEntity.getBirth())
                .gender(userEntity.getGender())
                .userRole(userEntity.getUserRole())
                .createAt(userEntity.getCreatedAt())
                .build();
    }

    public void updateInfo(UpdateUsers.UPDATE_USER updateUser){
        this.name = updateUser.getName();
        this.birth = updateUser.getBirth();
        this.gender = Gender.of(updateUser.getGender());
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

    public void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress){
        this.address = Address.builder()
                .zipcode(updateAddress.getZipCode())
                .address1(updateAddress.getAddr1())
                .address2(updateAddress.getAddr2())
                .build();
    }
}
