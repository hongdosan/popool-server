package kr.co.popoolserver.score.domain;

import kr.co.popoolserver.common.domain.BaseEntity;
import kr.co.popoolserver.corporate.domain.CorporateEntity;
import kr.co.popoolserver.user.domain.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_score")
@Getter
@AttributeOverride(name = "id", column = @Column(name = "score_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreEntity extends BaseEntity {

    @Column(name = "attendance")
    private int attendance;

    @Column(name = "sincerity")
    private int sincerity;

    @Column(name = "positiveness")
    private int positiveness;

    @Column(name = "technical")
    private int technical;

    @Column(name = "cooperative")
    private int cooperative;

    @OneToOne
    @JoinColumn(name = "corporate_id")
    private CorporateEntity corporateEntity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
