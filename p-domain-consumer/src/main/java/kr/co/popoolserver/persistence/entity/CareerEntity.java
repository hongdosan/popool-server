package kr.co.popoolserver.persistence.entity;

import kr.co.popoolserver.dtos.response.ResponseCareers;
import kr.co.popoolserver.persistence.BaseEntity;
import kr.co.popoolserver.dtos.request.CreateCareers;
import kr.co.popoolserver.dtos.request.UpdateCareers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "tbl_careers")
@Getter
@Where(clause = "is_deleted = 0")
@AttributeOverride(name = "id", column = @Column(name = "careers_id"))
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

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    @Builder
    public CareerEntity(String officePeriod,
                        String officeContext,
                        String officeName,
                        String officeSkill,
                        String portfolioUrl,
                        UserEntity userEntity) {
        this.officePeriod = officePeriod;
        this.officeContext = officeContext;
        this.officeName = officeName;
        this.officeSkill = officeSkill;
        this.portfolioUrl = portfolioUrl;
        this.userEntity = userEntity;
    }

    public static CareerEntity of(CreateCareers.CREATE_CAREER createCareer,
                                  UserEntity userEntity){
        return CareerEntity.builder()
                .officePeriod(createCareer.getOfficePeriod())
                .officeContext(createCareer.getOfficeContext())
                .officeName(createCareer.getOfficeName())
                .officeSkill(createCareer.getOfficeSkill())
                .portfolioUrl(createCareer.getPortfolioUrl())
                .userEntity(userEntity)
                .build();
    }

    public static ResponseCareers.READ_CAREER toDto(CareerEntity careerEntity){
        return ResponseCareers.READ_CAREER.builder()
                .officePeriod(careerEntity.getOfficePeriod())
                .officeContext(careerEntity.getOfficeContext())
                .officeName(careerEntity.getOfficeName())
                .officeSkill(careerEntity.getOfficeSkill())
                .portfolioUrl(careerEntity.getPortfolioUrl())
                .createAt(careerEntity.createdAt)
                .name(careerEntity.userEntity.getName())
                .build();
    }

    public void updateCareer(UpdateCareers.UPDATE_CAREER updateCareer){
        this.officePeriod = updateCareer.getOfficePeriod();
        this.officeContext = updateCareer.getOfficeContext();
        this.officeName = updateCareer.getOfficeName();
        this.officeSkill = updateCareer.getOfficeSkill();
        this.portfolioUrl = updateCareer.getPortfolioUrl();
    }
}
