package kr.co.popoolserver.domain;

import kr.co.popoolserver.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_admin")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "identity", unique = true, nullable = false, length = 25)
    private String identity;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Builder
    public AdminEntity(String identity,
                       String password,
                       String name,
                       UserRole userRole) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }

    public static AdminEntity of(AdminDto.CREATE create, PasswordEncoder passwordEncoder){
        return AdminEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .userRole(UserRole.ROLE_ADMIN)
                .build();
    }

    public static AdminDto.READ of(AdminEntity adminEntity){
        return AdminDto.READ.builder()
                .name(adminEntity.name)
                .build();
    }
}
