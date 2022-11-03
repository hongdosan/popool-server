package kr.co.popoolserver.user.domain;

import kr.co.popoolserver.common.domain.Address;
import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.domain.enums.Gender;
import kr.co.popoolserver.common.domain.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                      String email,
                      String name,
                      String birth,
                      Gender gender,
                      PhoneNumber phoneNumber) {
        this.identity = identity;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userRole = UserRole.ROLE_USER;
    }
}
