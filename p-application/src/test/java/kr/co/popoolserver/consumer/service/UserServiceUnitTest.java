package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.persistence.entity.UserEntity;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.persistence.repository.UserRepository;
import kr.co.popoolserver.service.RedisService;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisService redisService;

    @Test
    @DisplayName("일반 회원 등록 - 성공")
    void createUser_success() {
        //given
        CreateUsers.CREATE_USER createUser = UserCreators.createUserDto();
        UserEntity userEntity = UserCreators.createUser();
        given(userRepository.save(any())).willReturn(userEntity);
        given(userRepository.existsByIdentity(anyString())).willReturn(false);
        given(userRepository.existsByPhoneNumber(any())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(false);

        //when, then
        assertDoesNotThrow(() -> userService.createUser(createUser));
    }

    @Test
    @DisplayName("일반 회원 로그인 - 성공")
    void login_success() {
        //given
        CreateUsers.LOGIN login = UserCreators.createLoginDto();
        UserEntity userEntity = UserCreators.createUser();
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtProvider.createAccessToken(anyString(), any(UserRole.class), anyString())).willReturn("accessToken");
        given(jwtProvider.createRefreshToken(anyString(), any(UserRole.class), anyString())).willReturn("refreshToken");

        //when
        ResponseUsers.TOKEN token = userService.login(login);

        //then
        assertNotNull(token);
        assertNotEquals(token.getAccessToken(), token.getRefreshToken());
    }

    @Test
    @DisplayName("사용자 조회 - 성공")
    public void getUser_success() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            utl.when(UserThreadLocal::get).thenReturn(expected);

            // when
            ResponseUsers.READ_USER result = userService.getUser();

            //then
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBirth(), result.getBirth());
            assertEquals(expected.getGender(), result.getGender());
            assertEquals(expected.getUserRole(), result.getUserRole());
        }
    }

    @Test
    @DisplayName("사용자 세부 조회 - 성공")
    void getUserDetail_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            expected.updateAddress(UserCreators.updateAddressDto());
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when
            ResponseUsers.READ_USER_DETAIL readUserDetail = userService.getUserDetail();

            //then
            assertEquals(expected.getEmail(), readUserDetail.getEmail());
            assertEquals(expected.getPhoneNumber(), readUserDetail.getPhoneNumber());
            assertEquals(expected.getAddress(), readUserDetail.getAddress());
        }
    }

    @Test
    @DisplayName("사용자 정보 변경 - 성공")
    void updateUser_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.UPDATE_USER updateUser = UserCreators.updateUserDto();
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.updateUser(updateUser));
        }
    }

    @Test
    @DisplayName("사용자 비밀번호 변경 - 성공")
    void updatePassword_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.UPDATE_PASSWORD updatePassword = UserCreators.updatePasswordDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.updatePassword(updatePassword));
        }
    }

    @Test
    @DisplayName("사용자 메일 변경 - 성공")
    void updateEmail_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.UPDATE_EMAIL updateEmail = UserCreators.updateEmailDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.updateEmail(updateEmail));
        }
    }

    @Test
    @DisplayName("사용자 전화번호 변경 - 성공")
    void updatePhoneNumber_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber = UserCreators.updatePhoneNumberDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.updatePhoneNumber(updatePhoneNumber));
        }
    }

    @Test
    @DisplayName("사용자 주소 변경 - 성공")
    void updateAddress_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.UPDATE_ADDRESS updateAddress = UserCreators.updateAddressDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.updateAddress(updateAddress));
        }
    }

    @Test
    @DisplayName("사용자 삭제 - 성공")
    void deleteUser_success() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            UpdateUsers.DELETE delete = UserCreators.deleteUserDto();
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            utl.when(UserThreadLocal::get).thenReturn(expected);

            //when, then
            assertDoesNotThrow(() -> userService.deleteUser(delete));
        }
    }

    @Test
    @DisplayName("사용자 복구 - 성공")
    void restoreUser_success() {
        //given
        UserEntity userEntity = UserCreators.createUser();
        userEntity.deleted();
        UpdateUsers.RESTORE restore = UserCreators.restoreUserDto();
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        //when, then
        assertDoesNotThrow(() -> userService.restoreUser(restore));
    }
}
