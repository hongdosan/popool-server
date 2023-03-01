package kr.co.popoolserver.consumer.domain;

import kr.co.popoolserver.dtos.request.CreateCareers;
import kr.co.popoolserver.persistence.entity.CareerEntity;

public class CareerCreators {
    public static CareerEntity createCareer(){
        return CareerEntity.builder()
                .officeContext("webtoon")
                .officeName("naver")
                .officePeriod("5")
                .officeSkill("spring")
                .portfolioUrl("github.com")
                .userEntity(UserCreators.createUser())
                .build();
    }

    public static CreateCareers.CREATE_CAREER createCareerDto(){
        return CreateCareers.CREATE_CAREER.builder()
                .officeContext("webtoon")
                .officeName("naver")
                .officePeriod("5")
                .officeSkill("spring")
                .portfolioUrl("github.com")
                .build();
    }
}
