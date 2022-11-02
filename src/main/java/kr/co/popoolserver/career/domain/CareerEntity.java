package kr.co.popoolserver.career.domain;

import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.common.domain.enums.GradeType;
import kr.co.popoolserver.common.domain.enums.ProductType;
import kr.co.popoolserver.corporate.domain.CorporateEntity;
import kr.co.popoolserver.user.domain.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_career")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "career_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerEntity extends BaseEntity {

    @Column(name = "office_period")
    private String officePeriod;

    @Column(name = "office_context")
    private String officeContext;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "office_skill")
    private String officeSkill;

    @Column(name = "popool_url")
    private String popoolUrl;

    //TODO : 이미지 업로드
    //TODO : 파일 업로드
    @Column(name = "popool_file")
    private String popoolFile;

    @Column(name = "grade_type")
    @Enumerated(value = EnumType.STRING)
    private GradeType gradeType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
