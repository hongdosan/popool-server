package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.domain.CareerCreators;
import kr.co.popoolserver.consumer.domain.CorporateCreators;
import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.dtos.request.CreateCareers;
import kr.co.popoolserver.persistence.entity.CareerEntity;
import kr.co.popoolserver.persistence.entity.CorporateEntity;
import kr.co.popoolserver.persistence.entity.UserEntity;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.persistence.repository.CareerFileRepository;
import kr.co.popoolserver.persistence.repository.CareerRepository;
import kr.co.popoolserver.service.S3Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CareerServiceUnitTest {

    @InjectMocks
    private CareerService careerService;

    @Mock
    private CareerRepository careerRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    private CareerFileRepository careerFileRepository;

    @Test
    void createCareer_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            CareerEntity careerEntity = CareerCreators.createCareer();
            CreateCareers.CREATE_CAREER createCareer = CareerCreators.createCareerDto();
            given(careerRepository.save(any())).willReturn(careerEntity);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //then, when
            assertDoesNotThrow(() -> careerService.createCareer(UserType.USER, createCareer));
        }
    }

    @Test
    void createCareerFile_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void getAllCareers_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void getCareer_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void getOthersCareer_success() {
        // given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void getCareerFileDownload_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void updateCareer_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void deleteCareer_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }

    @Test
    void deleteCareerFile_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

        }
    }
}