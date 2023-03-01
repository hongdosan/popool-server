package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.consumer.domain.UserCreators;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.entity.user.model.Address;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.user.UserRepository;
import kr.co.popoolserver.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void createUser() {
    }

    @Test
    void login() {
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
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity expected = UserCreators.createUser();
            expected.updateAddress(UserCreators.createAddress());
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
    @DisplayName("")
    void updateUser() {
    }

    @Test
    @DisplayName("")
    void updatePassword() {
    }

    @Test
    @DisplayName("")
    void updateEmail() {
    }

    @Test
    @DisplayName("")
    void updatePhoneNumber() {
    }

    @Test
    @DisplayName("")
    void updateAddress() {
    }

    @Test
    @DisplayName("")
    void deleteUser() {
    }

    @Test
    @DisplayName("")
    void restoreUser() {
    }
}
