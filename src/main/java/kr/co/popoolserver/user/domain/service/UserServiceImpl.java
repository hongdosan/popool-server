package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void signUp(UserDto.CREATE create) {
        //TODO: 회원가입
    }

    @Override
    public void updateUser(UserDto userDto) {
        //TODO: update user
    }

    @Override
    public void getUser() {
        //TODO: get user
    }

    @Override
    public void deleteUser() {
        //TODO: delete user
    }

    @Override
    public void checkIdentity(String identity) {
        //TODO: 아이디 중복 체크
    }

    @Override
    public void checkPhoneNumber(String phoneNumber) {
        //TODO: 전화번호 중복 체크
    }

    @Override
    public void checkPassword(String password, String checkPassword) {
        //TODO: 비밀번호 체크
    }
}
