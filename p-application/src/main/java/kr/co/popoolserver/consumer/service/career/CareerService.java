package kr.co.popoolserver.consumer.service.career;

import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.dtos.S3Dto;
import kr.co.popoolserver.dtos.request.CreateCareers;
import kr.co.popoolserver.dtos.request.UpdateCareers;
import kr.co.popoolserver.dtos.response.ResponseCareers;
import kr.co.popoolserver.entity.career.CareerEntity;
import kr.co.popoolserver.entity.career.CareerFileEntity;
import kr.co.popoolserver.entity.user.CorporateEntity;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.repository.career.CareerFileRepository;
import kr.co.popoolserver.repository.career.CareerRepository;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;

    private final CareerFileRepository careerFileRepository;

    private final S3Service s3Service;

    private final JwtProvider jwtProvider;

    @Transactional
    public void createCareer(UserType userType,
                             CreateCareers.CREATE_CAREER createCareer){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        final CareerEntity careerEntity = CareerEntity.of(createCareer, userEntity);
        careerRepository.save(careerEntity);
    }

    @Transactional
    public void createCareerFile(UserType userType,
                                 MultipartFile multipartFile){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        isCareerFile(userEntity, userType);

        final S3Dto.CONVERT convert = s3Service.uploadS3(multipartFile, "image");
        careerFileRepository.save(CareerFileEntity.of(convert, userEntity));
    }

    public List<ResponseCareers.READ_CAREER> getAllCareers(UserType userType){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        return careerRepository.findByUserEntity(userEntity).stream()
                .map(CareerEntity::toDto).collect(Collectors.toList());
    }

    public ResponseCareers.READ_CAREER getCareer(UserType userType,
                                                 Long careerId){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        return CareerEntity.toDto(careerRepository.findByIdAndUserEntity(careerId, userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER)));
    }

    public ResponseCareers.READ_CAREER getOthersCareer(UserType userType,
                                                       Long careerId){
        final CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkCorporateType(userType);
        checkCorporateRole(corporateEntity.getUserRole());

        return CareerEntity.toDto(careerRepository.findById(careerId)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER)));
    }

    public S3Dto.DOWNLOAD getCareerFileDownload(UserType userType){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        final CareerFileEntity careerFileEntity = careerFileRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_EMPTY));

        return s3Service.downloadS3(careerFileEntity.getFileName());
    }

    @Transactional
    public void updateCareer(UserType userType,
                             UpdateCareers.UPDATE_CAREER updateCareer){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        CareerEntity careerEntity = careerRepository.findByIdAndUserEntity(updateCareer.getId(), userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));

        careerEntity.updateCareer(updateCareer);
        careerRepository.save(careerEntity);
    }

    @Transactional
    public void deleteCareer(UserType userType,
                             Long careerId){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        final CareerEntity careerEntity = careerRepository.findByIdAndUserEntity(careerId, userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_CAREER));

        careerRepository.delete(careerEntity);
    }

    @Transactional
    public void deleteCareerFile(UserType userType){
        final UserEntity userEntity = UserThreadLocal.get();

        checkUserType(userType);
        checkUserRole(userEntity.getUserRole());

        final CareerFileEntity careerFileEntity = careerFileRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.FAIL_FILE_EMPTY));

        s3Service.deleteS3(careerFileEntity.getFileName());
        careerFileRepository.delete(careerFileEntity);
    }

    private void isCareerFile(UserEntity userEntity,
                              UserType userType){
        if(careerFileRepository.existsByUserEntity(userEntity)){
            deleteCareerFile(userType);
        }
    }

    private void checkUserType(UserType userType){
        if(!userType.equals(UserType.USER)){
            new BusinessLogicException("잘못된 user type");
        }
    }

    private void checkCorporateType(UserType userType){
        if(!userType.equals(UserType.CORPORATE)){
            new BusinessLogicException("잘못된 corporate type");
        }
    }

    private void checkUserRole(UserRole userRole){
        if(!userRole.equals(UserRole.ROLE_USER)){
            new BusinessLogicException("잘못된 user role");
        }
    }

    private void checkCorporateRole(UserRole userRole){
        if(!userRole.equals(UserRole.ROLE_CORPORATE)){
            new BusinessLogicException("잘못된 user role");
        }
    }
}
