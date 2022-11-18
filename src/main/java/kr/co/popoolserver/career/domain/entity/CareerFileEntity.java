package kr.co.popoolserver.career.domain.entity;

import kr.co.popoolserver.common.domain.BaseEntity;
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

    @Column(name = "file_extension_index")
    private int fileExtensionIndex;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public CareerFileEntity(String fileName,
                            long fileSize,
                            String fileUrl,
                            String fileExtension,
                            int fileExtensionIndex,
                            UserEntity userEntity) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.fileExtension = fileExtension;
        this.fileExtensionIndex = fileExtensionIndex;
        this.userEntity = userEntity;
    }

    public static CareerFileEntity of(String fileName,
                                      long fileSize,
                                      String fileUrl,
                                      String fileExtension,
                                      int fileExtensionIndex,
                                      UserEntity userEntity){
        return CareerFileEntity.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(fileUrl)
                .fileExtension(fileExtension)
                .fileExtensionIndex(fileExtensionIndex)
                .userEntity(userEntity)
                .build();
    }
}
