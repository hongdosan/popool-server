package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.user.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
