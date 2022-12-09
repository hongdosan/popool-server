package kr.co.popoolserver.infrastructure.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of="id", callSuper = false)
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updateAt;

    @Column(name = "del_yn", nullable = false, length = 1)
    protected String deyYN = "N";

    public void deleted(){
        this.deyYN = "Y";
    }

    public void reCreated(){
        this.deyYN = "N";
    }
}
