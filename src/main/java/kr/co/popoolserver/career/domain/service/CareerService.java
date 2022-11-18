package kr.co.popoolserver.career.domain.service;

import kr.co.popoolserver.career.domain.dto.CareerDto;
import kr.co.popoolserver.career.domain.entity.CareerEntity;
import kr.co.popoolserver.career.repository.CareerRepository;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;

    /**
     * 이력서 생성
     * @param create
     */
    @Transactional
    public void createCareer(CareerDto.CREATE create){
        UserEntity userEntity = UserThreadLocal.get();
        CareerEntity careerEntity = CareerEntity.of(create, userEntity);
        careerRepository.save(careerEntity);
    }

    /**
     * 본인 이력서 조회
     * @return
     */
    public CareerDto.READ getCareer(){
        UserEntity userEntity = UserThreadLocal.get();
        CareerEntity careerEntity = careerRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));
        return CareerEntity.of(careerEntity);
    }
}
