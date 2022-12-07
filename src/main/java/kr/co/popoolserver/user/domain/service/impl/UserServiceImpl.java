package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.domain.enums.ServiceName;
import kr.co.popoolserver.common.infra.error.exception.BadRequestException;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.DuplicatedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.UserThreadLocal;
import kr.co.popoolserver.common.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import kr.co.popoolserver.user.domain.dto.UserDto;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import kr.co.popoolserver.user.domain.service.RedisService;
import kr.co.popoolserver.user.domain.service.UserService;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    /**
     * login service
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     */
    @Override
    public UserCommonDto.TOKEN login(UserCommonDto.LOGIN login) {
        UserEntity userEntity = userRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkEncodePassword(login.getPassword(), userEntity.getPassword());
        checkDelete(userEntity.getDeyYN());

        String[] tokens = generateToken(userEntity);
        redisService.createData(userEntity.getIdentity(), tokens[1], REFRESH_EXPIRE);

        return UserCommonDto.TOKEN.builder()
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
    @Transactional
    public void signUp(UserDto.CREATE create) {
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
    @Transactional
    public void updateUser(UserDto.UPDATE update) {
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
    @Transactional
    public void updatePassword(UserCommonDto.UPDATE_PASSWORD password) {
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
    @Transactional
    public void updateEmail(UserCommonDto.UPDATE_EMAIL email) {
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
    @Transactional
    public void updatePhone(UserCommonDto.UPDATE_PHONE phone) {
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
    @Transactional
    public void updateAddress(UserCommonDto.UPDATE_ADDRESS address) {
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
    public UserDto.READ getUser() {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        return UserEntity.of(userEntity);
    }

    /**
     * 본인 주소 조회
     * @return READ_ADDRESS
     */
    @Override
    public UserCommonDto.READ_ADDRESS getAddress() {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        return UserCommonDto.READ_ADDRESS.builder()
                .address(userEntity.getAddress())
                .build();
    }

    /**
     * 본인 메일 조회
     * @return READ_EMAIL
     */
    @Override
    public UserCommonDto.READ_EMAIL getEmail() {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        return UserCommonDto.READ_EMAIL.builder()
                .email(userEntity.getEmail())
                .build();
    }

    /**
     * 본인 번호 조회
     * @return READ_PHONE
     */
    @Override
    public UserCommonDto.READ_PHONE getPhone() {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        return UserCommonDto.READ_PHONE.builder()
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }

    /**
     * 회원 탈퇴
     * @param delete
     */
    @Override
    @Transactional
    public void deleteUser(UserDto.DELETE delete) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(delete.getOriginalPassword(), userEntity.getPassword());
        userEntity.deleted();
        userRepository.save(userEntity);
    }

    /**
     * 탈퇴 회원 복구
     * @param reCreate
     */
    @Override
    @Transactional
    public void reCreateUser(UserDto.RE_CREATE reCreate) {
        UserEntity userEntity = userRepository.findByIdentity(reCreate.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkReCreate(userEntity.getDeyYN());
        checkEncodePassword(reCreate.getOriginalPassword(), userEntity.getPassword());
        userEntity.reCreated();
        userRepository.save(userEntity);
    }

    /**
     * Redis에 저장된 RefreshToken 삭제
     * @param identity
     */
    @Override
    public void deleteRefreshToken(String identity){
        redisService.deleteData(identity);
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

    @Override
    public Boolean canHandle(ServiceName serviceName) {
        return serviceName.equals(ServiceName.USER);
    }
}
