package kr.co.popoolserver.career.domain.entity;

import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.domain.BaseEntity;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_career_file")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "career_file_id"))
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

    @OneToOne
    @JoinColumn(name = "user_id")
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
    }

    public static CareerFileEntity of(CareerFileDto.CONVERT convert,
                                      UserEntity userEntity) {
        return CareerFileEntity.builder()
                .fileName(convert.getFileName())
                .fileSize(convert.getFileSize())
                .fileUrl(convert.getFileUrl())
                .fileExtension(convert.getFileExtension())
                .userEntity(userEntity)
                .build();
    }

    public static CareerFileDto.READ_INFO of(CareerFileEntity careerFileEntity) {
        return CareerFileDto.READ_INFO.builder()
                .fileName(careerFileEntity.getFileName())
                .fileSize(careerFileEntity.getFileSize())
                .fileExtension(careerFileEntity.getFileExtension())
                .identity(careerFileEntity.getUserEntity().getIdentity())
                .createAt(careerFileEntity.createdAt)
                .build();
    }

}