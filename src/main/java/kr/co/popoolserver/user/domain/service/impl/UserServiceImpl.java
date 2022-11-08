package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.DuplicatedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.common.infra.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import kr.co.popoolserver.user.domain.dto.userDto.UserCreateDto;
import kr.co.popoolserver.user.domain.service.UserService;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * login service
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     */
    @Override
    public UserCreateDto.TOKEN login(UserCreateDto.LOGIN login) {
        UserEntity userEntity = userRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkEncodePassword(login.getPassword(), userEntity.getPassword());
        checkDelete(userEntity.getDeyYN());
        String[] tokens = generateToken(userEntity);

        return UserCreateDto.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    /**
     * Token Create
     * @param userEntity
     * @return
     */
    private String[] generateToken(UserEntity userEntity){
        String accessToken = jwtProvider.createAccessToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());
        String refreshToken = jwtProvider.createRefreshToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());
        return new String[]{accessToken, refreshToken};
    }

    /**
     * signUp Service
     * @param create : user info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Override
    public void signUp(UserCreateDto.CREATE create) {
        checkIdentity(create.getIdentity());
        checkPassword(create.getPassword(), create.getCheckPassword());
        checkPhoneNumber(create.getPhone());

        final UserEntity userEntity = UserEntity.of(create, passwordEncoder);
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(UserCreateDto userCreateDto) {
        //TODO: update user
    }

    @Override
    public UserGetDto.READ getUser() {
        UserEntity userEntity = UserThreadLocal.get();
        return UserGetDto.READ.of(userEntity);
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
        if(!password.equals(checkPassword)) throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
    }

    /**
     * Login PW Check
     * @param password
     * @param encodePassword
     */
    @Override
    public void checkEncodePassword(String password, String encodePassword) {
        if(!passwordEncoder.matches(password, encodePassword)) throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
    }

    /**
     * delete Check
     * @param delYN
     */
    @Override
    public void checkDelete(String delYN) {
        if(delYN.equals("Y")) throw new BusinessLogicException(ErrorCode.DELETED_USER);
    }
}
