package kr.co.popoolserver.persistence.entity;

import kr.co.popoolserver.dtos.S3Dto;
import kr.co.popoolserver.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "tbl_career_files")
@Getter
@Where(clause = "is_deleted = 0")
@AttributeOverride(name = "id", column = @Column(name = "career_files_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerFileEntity extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_extension")
    private String fileExtension;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    @Builder
    public CareerFileEntity(String fileName,
                            long fileSize,
                            String fileUrl,
                            String fileExtension,
                            UserEntity userEntity) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.fileExtension = fileExtension;
        this.userEntity = userEntity;
        this.isDeleted = 0;
    }

    public static CareerFileEntity of(S3Dto.CONVERT convert,
                                      UserEntity userEntity) {
        return CareerFileEntity.builder()
                .fileName(convert.getFileName())
                .fileSize(convert.getFileSize())
                .fileUrl(convert.getFileUrl())
                .fileExtension(convert.getFileExtension())
                .userEntity(userEntity)
                .build();
    }
}