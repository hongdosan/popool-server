package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.infra.error.exception.BadRequestException;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.DuplicatedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.common.infra.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.dto.userDto.UserDeleteDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserGetDto;
import kr.co.popoolserver.user.domain.dto.userDto.UserUpdateDto;
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

    /**
     * 본인 기본 정보 수정 (이름, 성별, 생년월일)
     * @param update
     */
    @Override
    public void updateUser(UserUpdateDto.UPDATE update) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        userEntity.updateInfo(update);
        userRepository.save(userEntity);
    }

    /**
     * 본인 비밀번호 수정
     * @param password
     */
    @Override
    public void updatePassword(UserUpdateDto.PASSWORD password) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(password.getOriginalPassword(), userEntity.getPassword());
        checkPassword(password.getNewPassword(), password.getNewCheckPassword());
        userEntity.updatePassword(passwordEncoder.encode(password.getNewPassword()));
        userRepository.save(userEntity);
    }

    /**
     * Email update service
     * @param email
     */
    @Override
    public void updateEmail(UserUpdateDto.EMAIL email) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(email.getOriginalPassword(), userEntity.getPassword());
        checkEmail(email.getEmail());
        userEntity.updateEmail(email.getEmail());
        userRepository.save(userEntity);
    }

    /**
     * Phone update service
     * @param phone
     */
    @Override
    public void updatePhone(UserUpdateDto.PHONE phone) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(phone.getOriginalPassword(), userEntity.getPassword());
        checkPhoneNumber(phone.getNewPhoneNumber());
        userEntity.updatePhone(new PhoneNumber(phone.getNewPhoneNumber()));
        userRepository.save(userEntity);
    }

    /**
     * Address update service
     * @param address
     */
    @Override
    public void updateAddress(UserUpdateDto.ADDRESS address) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(address.getOriginalPassword(), userEntity.getPassword());
        userEntity.updateAddress(address);
        userRepository.save(userEntity);
    }

    /**
     * 본인 회원 정보 조회
     * @return : UserGetDto.READ
     */
    @Override
    public UserGetDto.READ getUser() {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        return UserGetDto.READ.of(userEntity);
    }

    /**
     * 회원 탈퇴
     * @param password
     */
    @Override
    public void deleteUser(String password) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(password, userEntity.getPassword());
        userEntity.deleted();
        userRepository.save(userEntity);
    }

    /**
     * 탈퇴 회원 복구
     * @param reCreate
     */
    @Override
    public void reCreateUser(UserDeleteDto.RE_CREATE reCreate) {
        UserEntity userEntity = userRepository.findByIdentity(reCreate.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkReCreate(userEntity.getDeyYN());
        checkEncodePassword(reCreate.getOriginalPassword(), userEntity.getPassword());
        userEntity.reCreated();
        userRepository.save(userEntity);
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
     * Email duplicated check
     * @param email
     */
    @Override
    public void checkEmail(String email) {
        if(userRepository.existsByEmail(email)) throw new DuplicatedException(ErrorCode.DUPLICATED_EMAIL);
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

    /**
     * reCreate Check Service
     * @param delYN
     */
    @Override
    public void checkReCreate(String delYN) {
        if(delYN.equals("N")) throw new BadRequestException("탈퇴되지 않은 회원입니다.");
    }
}
