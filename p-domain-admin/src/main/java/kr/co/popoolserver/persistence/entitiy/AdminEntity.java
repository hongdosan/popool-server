package kr.co.popoolserver.persistence.entitiy;

import kr.co.popoolserver.dtos.RequestAdmin;
import kr.co.popoolserver.dtos.ResponseAdmin;
import kr.co.popoolserver.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(name = "identity", unique = true, nullable = false)
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String identity;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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

    public static AdminEntity of(RequestAdmin.CREATE_ADMIN createAdmin,
                                 PasswordEncoder passwordEncoder){
        return AdminEntity.builder()
                .identity(createAdmin.getIdentity())
                .password(passwordEncoder.encode(createAdmin.getPassword()))
                .name(createAdmin.getName())
                .userRole(UserRole.ROLE_ADMIN)
                .build();
    }

    public static ResponseAdmin.READ_ADMIN toDto(AdminEntity adminEntity){
        return ResponseAdmin.READ_ADMIN.builder()
                .name(adminEntity.name)
                .build();
    }
}
