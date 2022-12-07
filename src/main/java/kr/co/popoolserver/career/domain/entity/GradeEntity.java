package kr.co.popoolserver.career.domain.entity;

import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_grade")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "grade_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradeEntity extends BaseEntity {

    @Column(name = "attendance_avg")
    private double attendanceAvg;

    @Column(name = "sincerity_avg")
    private double sincerityAvg;

    @Column(name = "positiveness_avg")
    private double positivenessAvg;

    @Column(name = "technical_avg")
    private double technicalAvg;

    @Column(name = "cooperative_avg")
    private double cooperativeAvg;

    @Column(name = "total_avg")
    private double totalAvg;

    @Column(name = "grade_amount")
    private int gradeAmount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
