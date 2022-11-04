package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.user.domain.dto.UserCreateDto;
import kr.co.popoolserver.user.domain.service.UserService;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void updateUser(UserCreateDto userCreateDto) {
        //TODO: update user
    }

}
