package kr.co.popoolserver.career.domain.service;

import kr.co.popoolserver.career.domain.dto.CareerFileDto;
import kr.co.popoolserver.career.domain.entity.CareerFileEntity;
import kr.co.popoolserver.career.repository.CareerFileRepository;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.common.s3.S3Service;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerFileService {

    private final CareerFileRepository careerFileRepository;
    private final S3Service s3Service;

    /**
     * S3 Image File 저장 & DB Image Meta Data 저장 Service
     * @param multipartFile
     */
    @Transactional
    public void createCareerFile(MultipartFile multipartFile){
        CareerFileDto.CONVERT convert = s3Service.uploadS3(multipartFile, "image");
        UserEntity userEntity = UserThreadLocal.get();

        careerFileRepository.save(CareerFileEntity.of(convert, userEntity));
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

    /**
     * File Delete Service
     */
    @Transactional
    public void deleteCareerFile(){
        UserEntity userEntity = UserThreadLocal.get();
        CareerFileEntity careerFileEntity = careerFileRepository.findByUserEntity(userEntity)
                        .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_EMPTY));
        careerFileRepository.delete(careerFileEntity);
    }
}
