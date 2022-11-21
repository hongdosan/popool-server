package kr.co.popoolserver.career.domain.service;

import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.career.repository.CareerFileRepository;
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
public class CareerFileService {

    private final CareerFileRepository careerFileRepository;

    /**
     * File Create
     * @param convert
     * @param userEntity
     */
    @Transactional
    public void createCareerFile(CareerFileDto.CONVERT convert, UserEntity userEntity){
        CareerFileEntity careerFileEntity = CareerFileEntity.of(convert, userEntity);
        careerFileRepository.save(careerFileEntity);
    }

    /**
     * File Info Read
     * @return
     */
    public CareerFileDto.READ_INFO getCareerFileInfo(){
        UserEntity userEntity = UserThreadLocal.get();
        CareerFileEntity careerFileEntity = careerFileRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_UPLOAD));
        return CareerFileEntity.of(careerFileEntity);
    }
}
