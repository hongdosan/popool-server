package kr.co.popoolserver.career.domain.service;

import kr.co.popoolserver.career.domain.entity.CareerEntity;
import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.career.repository.CareerFileRepository;
import kr.co.popoolserver.career.repository.CareerRepository;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerFileService {

    private final CareerFileRepository careerFileRepository;
    private final CareerRepository careerRepository;

    /**
     * Career File 생성 및 변경
     * @param careerFileEntity
     */
    @Transactional
    public void uploadFile(CareerFileEntity careerFileEntity){
        CareerEntity careerEntity = careerRepository.findByUserEntity(careerFileEntity.getUserEntity())
                        .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));
        careerEntity.updateFile(careerFileEntity);

        careerRepository.save(careerEntity);
        careerFileRepository.save(careerFileEntity);
    }
}
