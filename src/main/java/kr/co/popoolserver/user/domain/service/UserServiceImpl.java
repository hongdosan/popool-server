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
     * login service
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     */
    @Override
    public UserDto.TOKEN login(UserDto.LOGIN login) {
        UserEntity userEntity = userRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLoginException(ErrorCode.WRONG_IDENTITY));
        checkEncodePassword(login.getPassword(), userEntity.getPassword());
        checkDelete(userEntity.getDeyYN());
        String[] tokens = generateToken(userEntity);

        return UserDto.TOKEN.builder()
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

    /**
     * Login PW Check
     * @param password
     * @param encodePassword
     */
    @Override
    public void checkEncodePassword(String password, String encodePassword) {
        if(!passwordEncoder.matches(password, encodePassword)) throw new BusinessLoginException(ErrorCode.WRONG_PASSWORD);
    }

    /**
     * delete Check
     * @param delYN
     */
    @Override
    public void checkDelete(String delYN) {
        if(delYN.equals("Y")) throw new BusinessLoginException(ErrorCode.DELETED_USER);
    }
}
