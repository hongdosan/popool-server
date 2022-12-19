package kr.co.popoolserver.career.domain.entity;

import kr.co.popoolserver.career.domain.dto.CareerDto;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String popolUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public CareerEntity(String officePeriod,
                        String officeContext,
                        String officeName,
                        String officeSkill,
                        String popolUrl,
                        UserEntity userEntity) {
        this.officePeriod = officePeriod;
        this.officeContext = officeContext;
        this.officeName = officeName;
        this.officeSkill = officeSkill;
        this.popolUrl = popolUrl;
        this.userEntity = userEntity;
    }

    public static CareerEntity of(CareerDto.CREATE create, UserEntity userEntity){
        return CareerEntity.builder()
                .officePeriod(create.getOfficePeriod())
                .officeContext(create.getOfficeContext())
                .officeName(create.getOfficeName())
                .officeSkill(create.getOfficeSkill())
                .popolUrl(create.getPopolUrl())
                .userEntity(userEntity)
                .build();
    }

    public static CareerDto.READ of(CareerEntity careerEntity){
        return CareerDto.READ.builder()
                .officePeriod(careerEntity.getOfficePeriod())
                .officeContext(careerEntity.getOfficeContext())
                .officeName(careerEntity.getOfficeName())
                .officeSkill(careerEntity.getOfficeSkill())
                .popolUrl(careerEntity.getPopolUrl())
                .createAt(careerEntity.createdAt)
                .name(careerEntity.userEntity.getName())
                .build();
    }

    public static List<CareerDto.READ> of(List<CareerEntity> careerEntities){
        List<CareerDto.READ> reads = new ArrayList<>();
        for(CareerEntity careerEntity : careerEntities){
            CareerDto.READ read = of(careerEntity);
            reads.add(read);
        }
        return reads;
    }

    public void updateCareer(CareerDto.UPDATE update){
        this.officePeriod = update.getOfficePeriod();
        this.officeContext = update.getOfficeContext();
        this.officeName = update.getOfficeName();
        this.officeSkill = update.getOfficeSkill();
        this.popolUrl = update.getPopolUrl();
    }
}
