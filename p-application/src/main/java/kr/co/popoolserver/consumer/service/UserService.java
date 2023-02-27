package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.UserEntity;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.user.UserRepository;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserCommonService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;

    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    /**
     * create user
     * @param createUser : create user info
     * @exception DuplicatedException : ID, Phone, Email Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Transactional
    public void createUser(CreateUsers.CREATE_USER createUser) {
        isIdentity(createUser.getIdentity());
        isPhoneNumber(createUser.getPhoneNumber());
        isEmail(createUser.getEmail());

        checkPassword(createUser.getPassword(), createUser.getCheckPassword());

        final UserEntity userEntity = UserEntity.of(createUser, passwordEncoder);
        userRepository.save(userEntity);
    }

    /**
     * login
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     * @exception BusinessLogicException
     */
    @Override
    public ResponseUsers.TOKEN login(CreateUsers.LOGIN login) {
        final UserEntity userEntity = userRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));

        checkEncodePassword(login.getPassword(), userEntity.getPassword(), passwordEncoder);
        checkDelete(userEntity.getDeyYN());

        final String[] tokens = generateToken(userEntity);
        redisService.createData(userEntity.getIdentity(), tokens[1], REFRESH_EXPIRE);

        return ResponseUsers.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    /**
     * Token Create
     * @param userEntity : login user
     * @return : accessToken, refreshToken
     */
    private String[] generateToken(UserEntity userEntity){
        final String accessToken = jwtProvider.createAccessToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());
        final String refreshToken = jwtProvider.createRefreshToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());

        return new String[]{accessToken, refreshToken};
    }

    /**
     * 본인 회원 정보 조회
     * @return ResponseUsers.READ_USER :  user info
     */
    public ResponseUsers.READ_USER getUser() {
        final UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());

        return UserEntity.of(userEntity);
    }

    /**
     * 본인 세부정보 조회
     * @return ResponseUsers.READ_DETAIL : address, phoneNumber, email
     */
    @Override
    public ResponseUsers.READ_USER_DETAIL getUserDetail() {
        final UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());

        return ResponseUsers.READ_USER_DETAIL.builder()
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .email(userEntity.getEmail())
                .build();
    }

    /**
     * 본인 기본 정보 수정
     * @param updateUser : update info
     */
    @Transactional
    public void updateUser(UpdateUsers.UPDATE_USER updateUser) {
        UserEntity userEntity = UserThreadLocal.get();
        checkDelete(userEntity.getDeyYN());
        userEntity.updateInfo(updateUser);

        userRepository.save(userEntity);
    }

    /**
     * 본인 비밀번호 수정
     * @param updatePassword : update pw info
     */
    @Override
    @Transactional
    public void updatePassword(UpdateUsers.UPDATE_PASSWORD updatePassword) {
        UserEntity userEntity = UserThreadLocal.get();

        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(updatePassword.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);
        checkPassword(updatePassword.getNewPassword(), updatePassword.getNewCheckPassword());

        userEntity.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        userRepository.save(userEntity);
    }

    /**
     * Email update service
     * @param updateEmail : update email info
     */
    @Override
    @Transactional
    public void updateEmail(UpdateUsers.UPDATE_EMAIL updateEmail) {
        UserEntity userEntity = UserThreadLocal.get();

        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(updateEmail.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);

        if(!userEntity.getEmail().equals(updateEmail.getEmail())){
            isEmail(updateEmail.getEmail());
            userEntity.updateEmail(updateEmail.getEmail());

            userRepository.save(userEntity);
        }
    }

    /**
     * Phone update service
     * @param updatePhoneNumber : update phone number info
     */
    @Override
    @Transactional
    public void updatePhoneNumber(UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber) {
        UserEntity userEntity = UserThreadLocal.get();

        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(updatePhoneNumber.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);

        if(!userEntity.getPhoneNumber().equals(updatePhoneNumber.getNewPhoneNumber())){
            isPhoneNumber(updatePhoneNumber.getNewPhoneNumber());
            userEntity.updatePhone(new PhoneNumber(updatePhoneNumber.getNewPhoneNumber()));

            userRepository.save(userEntity);
        }
    }

    /**
     * Address update service
     * @param updateAddress : update address info
     */
    @Override
    @Transactional
    public void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress) {
        UserEntity userEntity = UserThreadLocal.get();

        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(updateAddress.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);

        userEntity.updateAddress(updateAddress);
        userRepository.save(userEntity);
    }

    /**
     * 회원 탈퇴
     * @param delete : delete user info
     */
    @Override
    @Transactional
    public void deleteUser(UpdateUsers.DELETE delete) {
        UserEntity userEntity = UserThreadLocal.get();

        checkDelete(userEntity.getDeyYN());
        checkEncodePassword(delete.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);

        userEntity.deleted();
        userRepository.save(userEntity);
    }

    /**
     * 탈퇴 회원 복구
     * @param restore : restore info
     */
    @Override
    @Transactional
    public void restoreUser(UpdateUsers.RESTORE restore) {
        UserEntity userEntity = userRepository.findByIdentity(restore.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));

        checkRestore(userEntity.getDeyYN());
        checkEncodePassword(restore.getOriginalPassword(), userEntity.getPassword(), passwordEncoder);

        userEntity.restored();
        userRepository.save(userEntity);
    }

    /**
     * ID duplicated check
     * @param identity : user id
     */
    @Override
    public void isIdentity(String identity) {
        if(userRepository.existsByIdentity(identity)) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
        }
    }

    /**
     * Phone duplicated check
     * @param phoneNumber : user phone number
     */
    @Override
    public void isPhoneNumber(String phoneNumber) {
        if(userRepository.existsByPhoneNumber(new PhoneNumber(phoneNumber))) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_PHONE);
        }
    }

    /**
     * Email duplicated check
     * @param email : user email
     */
    @Override
    public void isEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Override
    public Boolean canHandle(UserType userType) {
        return userType.equals(UserType.USER);
    }
}
