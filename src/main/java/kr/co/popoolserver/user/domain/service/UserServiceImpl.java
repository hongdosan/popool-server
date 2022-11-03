package kr.co.popoolserver.user.domain.service;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.infra.error.exception.BusinessLoginException;
import kr.co.popoolserver.common.infra.error.exception.DuplicatedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.UserEntity;
import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * signUp Service
     * @param create : user info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLoginException : PW Check
     */
    @Override
    public void signUp(UserDto.CREATE create) {
        checkIdentity(create.getIdentity());
        checkPassword(create.getPassword(), create.getCheckPassword());
        checkPhoneNumber(create.getPhone());

        UserEntity userEntity = UserEntity.of(create, passwordEncoder);
        userRepository.save(userEntity);
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

    /**
     * ID duplicated check
     * @param identity
     */
    @Override
    public void checkIdentity(String identity) {
        if(userRepository.existsByIdentity(identity)) throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
    }

    /**
     * Phone duplicated check
     * @param phoneNumber
     */
    @Override
    public void checkPhoneNumber(String phoneNumber) {
        if(userRepository.existsByPhoneNumber(new PhoneNumber(phoneNumber))) throw new DuplicatedException(ErrorCode.DUPLICATED_PHONE);
    }

    /**
     * PW check
     * @param password : use pw
     * @param checkPassword : check pw
     */
    @Override
    public void checkPassword(String password, String checkPassword) {
        if(!password.equals(checkPassword)) throw new BusinessLoginException(ErrorCode.WRONG_PASSWORD);
    }
}
