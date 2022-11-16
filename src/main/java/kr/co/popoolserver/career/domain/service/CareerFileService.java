package kr.co.popoolserver.career.domain.service;

import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.career.repository.CareerFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CareerFileService {

    private final CareerFileRepository careerFileRepository;

    /**
     * Career File 생성
     * @param careerFileEntity
     */
    @Transactional
    public void createFile(CareerFileEntity careerFileEntity){
        careerFileRepository.save(careerFileEntity);
    }
}
