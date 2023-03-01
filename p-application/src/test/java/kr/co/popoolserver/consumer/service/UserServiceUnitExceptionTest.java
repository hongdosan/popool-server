package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.persistence.entity.UserEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.model.ErrorCode;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitExceptionTest {

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
    @DisplayName("일반 회원 등록 실패 아이디 중복 - DuplicateException")
    void createUser_already_identity_DuplicateException() {
        //given
        CreateUsers.CREATE_USER createUser = UserCreators.createUserDto();
        given(userRepository.existsByIdentity(any())).willReturn(true);

        //when, then
        assertThrows(DuplicatedException.class,
                () -> userService.createUser(createUser)).getMessage()
                .equals(ErrorCode.DUPLICATED_ID.getMessage());
    }

    @Test
    @DisplayName("일반 회원 등록 실패 전화번호 중복- DuplicateException")
    void createUser_already_phonenumber_DuplicateException() {
        //given
        CreateUsers.CREATE_USER createUser = UserCreators.createUserDto();
        given(userRepository.existsByPhoneNumber(any())).willReturn(true);

        //when, then
        assertThrows(DuplicatedException.class,
                () -> userService.createUser(createUser)).getMessage()
                .equals(ErrorCode.DUPLICATED_PHONE.getMessage());
    }

    @Test
    @DisplayName("일반 회원 등록 실패 이메일 중복 - DuplicateException")
    void createUser_already_email_DuplicateException() {
        //given
        CreateUsers.CREATE_USER createUser = UserCreators.createUserDto();
        given(userRepository.existsByEmail(any())).willReturn(true);

        //when, then
        assertThrows(DuplicatedException.class,
                () -> userService.createUser(createUser)).getMessage()
                .equals(ErrorCode.DUPLICATED_EMAIL.getMessage());
    }

    @Test
    @DisplayName("로그인 실패 비밀번호 오류 - BusinessLogicException")
    void login_wrong_password_BusinessLogicException() {
        //given
        CreateUsers.LOGIN login = UserCreators.createLoginDto();
        UserEntity userEntity = UserCreators.createUser();
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when, then
        assertThrows(BusinessLogicException.class,
                () -> userService.login(login)).getMessage()
                .equals(ErrorCode.WRONG_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("로그인 실패 없는 아이 - BusinessLogicException")
    void login_wrong_identity_BusinessLogicException() {
        //given
        CreateUsers.LOGIN login = UserCreators.createLoginDto();
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.empty());

        //when, then
        assertThrows(BusinessLogicException.class,
                () -> userService.login(login)).getMessage()
                .equals(ErrorCode.WRONG_IDENTITY.getMessage());
    }

    @Test
    @DisplayName("이미 삭제된 회원 - BusinessException")
    public void getUser_already_delete_user_to_business_exception() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            expected.deleted();
            utl.when(UserThreadLocal::get).thenReturn(expected);

            // when, then
            assertThrows(BusinessLogicException.class,
                    () -> userService.getUser()).getMessage().equals("탈퇴한 회원입니다.");
        }
    }

    @Test
    @DisplayName("이미 삭제된 회원 - BusinessException")
    public void getDetailUser_already_delete_user_to_business_exception() {
        // given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            expected.updateAddress(UserCreators.updateAddressDto());
            expected.deleted();
            utl.when(UserThreadLocal::get).thenReturn(expected);

            // when, then
            assertThrows(BusinessLogicException.class,
                    () -> userService.getUserDetail()).getMessage().equals("탈퇴한 회원입니다.");
        }
    }
}
