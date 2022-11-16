package kr.co.popoolserver.career.domain.entity;

import kr.co.popoolserver.common.domain.BaseEntity;
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
    @JoinColumn(name = "career_id")
    private CareerEntity careerEntity;

    @Builder
    public CareerFileEntity(String fileName,
                            long fileSize,
                            String fileUrl,
                            String fileExtension,
                            int fileExtensionIndex) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.fileExtension = fileExtension;
        this.fileExtensionIndex = fileExtensionIndex;
    }

    public static CareerFileEntity of(String fileName,
                                      long fileSize,
                                      String fileUrl,
                                      String fileExtension,
                                      int fileExtensionIndex){
        return CareerFileEntity.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(fileUrl)
                .fileExtension(fileExtension)
                .fileExtensionIndex(fileExtensionIndex)
                .build();
    }
}
