package kr.co.popoolserver.career.domain;

import kr.co.popoolserver.common.domain.BaseEntity;
import lombok.AccessLevel;
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
    private String fileSize;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_extension_index")
    private String fileExtensionIndex;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private CareerEntity careerEntity;
}
