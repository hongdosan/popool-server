package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.persistence.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@SpringBootConfiguration
@ComponentScan("kr.co.popoolserver")
@Import(UserService.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final String userIdentity = "user";
    private final String userPassword = "user";

    @Test
    @DisplayName("User Create & Login Test")
    void userCreateAndLogin_success() {
        //given
        CreateUsers.CREATE_USER createUser = createUser();
        CreateUsers.LOGIN login = loginUser();

        //when
        userService.createUser(createUser);
        ResponseUsers.TOKEN token = userService.login(login);

        //then
        assertThat(userRepository.existsByIdentity(createUser.getIdentity())).isTrue();
        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    private CreateUsers.CREATE_USER createUser(){
        return CreateUsers.CREATE_USER.builder()
                .phoneNumber("010-1231-1231")
                .password(userIdentity)
                .identity(userPassword)
                .gender("MALE")
                .checkPassword("user")
                .birth("19980000")
                .email("user@naver.com")
                .name("username")
                .build();
    }

    private CreateUsers.LOGIN loginUser(){
        return CreateUsers.LOGIN.builder()
                .identity(userIdentity)
                .password(userPassword)
                .build();
    }
}