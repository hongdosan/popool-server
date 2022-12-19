package kr.co.popoolserver.career.service;

import kr.co.popoolserver.career.domain.dto.CareerDto;
import kr.co.popoolserver.career.domain.entity.CareerEntity;
import kr.co.popoolserver.career.repository.CareerRepository;
import kr.co.popoolserver.auth.interceptor.UserThreadLocal;
import kr.co.popoolserver.entity.users.UserEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;
    private final JwtProvider jwtProvider;

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
     * 본인의 모든 이력서 조회
     * @return
     */
    public List<CareerDto.READ> getAllCareers(){
        UserEntity userEntity = UserThreadLocal.get();
        List<CareerEntity> careerEntities = careerRepository.findByUserEntity(userEntity);
        return CareerEntity.of(careerEntities);
    }

    /**
     * 본인 이력서 중 하나 조회
     * @return
     */
    public CareerDto.READ getCareer(Long id){
        UserEntity userEntity = UserThreadLocal.get();
        CareerEntity careerEntity = careerRepository.findByIdAndUserEntity(id, userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));
        return CareerEntity.of(careerEntity);
    }

    /**
     * 다른 사람 이력서 중 하나 조회 (권한 : 기업)
     * @return
     */
    public CareerDto.READ getOthersCareer(Long id){
        UserEntity userEntity = UserThreadLocal.get();
        jwtProvider.checkUserRole(userEntity.getUserRole());

        CareerEntity careerEntity = careerRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));
        return CareerEntity.of(careerEntity);
    }

    /**
     * 이력서 정보 변경 서비스
     * @param update
     */
    @Transactional
    public void updateCareer(CareerDto.UPDATE update){
        UserEntity userEntity = UserThreadLocal.get();
        CareerEntity careerEntity = careerRepository.findByIdAndUserEntity(update.getId(), userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));

        careerEntity.updateCareer(update);
    }

    /**
     * 본인 이력서 삭제 서비스
     * @param id
     */
    @Transactional
    public void deleteCareer(Long id){
        UserEntity userEntity = UserThreadLocal.get();
        CareerEntity careerEntity = careerRepository.findByIdAndUserEntity(id, userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));

        careerRepository.delete(careerEntity);
    }
}
