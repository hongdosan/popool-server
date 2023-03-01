package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.domain.CorporateCreators;
import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.CorporateEntity;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.user.CorporateRepository;
import kr.co.popoolserver.repository.user.UserRepository;
import kr.co.popoolserver.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CorporateServiceUnitTest {
    @InjectMocks
    private CorporateService corporateService;

    @Mock
    private CorporateRepository corporateRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisService redisService;

    @Test
    void createCorporate_success() {
        //given
        CreateUsers.CREATE_CORPORATE createCorporate = CorporateCreators.createCorporateDto();
        CorporateEntity corporateEntity = CorporateCreators.createCorporate();
        given(corporateRepository.save(any())).willReturn(corporateEntity);
        given(corporateRepository.existsByIdentity(anyString())).willReturn(false);

        //when, then
        assertDoesNotThrow(() -> corporateService.createCorporate(createCorporate));
    }

    @Test
    void login_success() {
        //given
        CreateUsers.LOGIN login = CorporateCreators.createLoginDto();
        CorporateEntity corporateEntity = CorporateCreators.createCorporate();
        given(corporateRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(corporateEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtProvider.createAccessToken(anyString(), any(UserRole.class), anyString())).willReturn("accessToken");
        given(jwtProvider.createRefreshToken(anyString(), any(UserRole.class), anyString())).willReturn("refreshToken");

        //when
        ResponseUsers.TOKEN token = corporateService.login(login);

        //then
        assertNotNull(token);
        assertNotEquals(token.getAccessToken(), token.getRefreshToken());
    }

    @Test
    void getCorporate_success() {
        // given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            // when
            ResponseUsers.READ_CORPORATE result = corporateService.getCorporate();

            //then
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBusinessName(), result.getBusinessName());
            assertEquals(expected.getBusinessCeoName(), result.getBusinessCeoName());
            assertEquals(expected.getBusinessNumber(), result.getBusinessNumber());
            assertEquals(expected.getUserRole(), result.getUserRole());
        }
    }

    @Test
    void getCorporateDetail_success() {
        // given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            // when
            ResponseUsers.READ_USER_DETAIL resultCorporateDetail = corporateService.getUserDetail();

            //then
            assertEquals(expected.getBusinessEmail(), resultCorporateDetail.getEmail());
            assertEquals(expected.getBusinessPhoneNumber(), resultCorporateDetail.getPhoneNumber());
            assertEquals(expected.getBusinessAddress(), resultCorporateDetail.getAddress());
        }
    }

    @Test
    void updateCorporate_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.UPDATE_CORPORATE updateCorporate = CorporateCreators.updateCorporateDto();
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.updateCorporate(updateCorporate));
        }
    }

    @Test
    void updatePassword_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.UPDATE_PASSWORD updatePassword = UserCreators.updatePasswordDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.updatePassword(updatePassword));
        }
    }

    @Test
    void updateEmail_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.UPDATE_EMAIL updateEmail = UserCreators.updateEmailDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.updateEmail(updateEmail));
        }
    }

    @Test
    void updatePhoneNumber_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber = UserCreators.updatePhoneNumberDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.updatePhoneNumber(updatePhoneNumber));
        }
    }

    @Test
    void updateAddress_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.UPDATE_ADDRESS updateAddress = UserCreators.updateAddressDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.updateAddress(updateAddress));
        }
    }

    @Test
    void deleteCorporate_success() {
        //given
        try (MockedStatic<CorporateThreadLocal> utl = Mockito.mockStatic(CorporateThreadLocal.class)) {
            CorporateEntity expected = CorporateCreators.createCorporate();
            UpdateUsers.DELETE delete = UserCreators.deleteUserDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(CorporateThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> corporateService.deleteUser(delete));
        }
    }

    @Test
    void restoreUser_success() {
        //given
        CorporateEntity corporateEntity = CorporateCreators.createCorporate();
        corporateEntity.deleted();
        UpdateUsers.RESTORE restore = UserCreators.restoreUserDto();
        given(corporateRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(corporateEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        //when, then
        assertDoesNotThrow(() -> corporateService.restoreUser(restore));
    }
}