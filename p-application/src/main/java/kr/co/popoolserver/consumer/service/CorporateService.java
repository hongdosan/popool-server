package kr.co.popoolserver.consumer.service;

import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.request.UpdateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.CorporateEntity;
import kr.co.popoolserver.entity.user.model.PhoneNumber;
import kr.co.popoolserver.enums.UserType;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.user.CorporateRepository;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporateService implements UserCommonService {

    private final CorporateRepository corporateRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final RedisService redisService;

    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    /**
     * create Corporate
     * @param createCorporate : create corporate info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Transactional
    public void createCorporate(CreateUsers.CREATE_CORPORATE createCorporate) {
        isIdentity(createCorporate.getIdentity());
        isPhoneNumber(createCorporate.getBusinessPhoneNumber());
        isEmail(createCorporate.getBusinessEmail());

        checkPassword(createCorporate.getPassword(), createCorporate.getCheckPassword());

        final CorporateEntity corporateEntity = CorporateEntity.of(createCorporate, passwordEncoder);
        corporateRepository.save(corporateEntity);
    }

    /**
     * login
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     * @exception BusinessLogicException
     */
    @Override
    public ResponseUsers.TOKEN login(CreateUsers.LOGIN login) {
        final CorporateEntity corporateEntity = corporateRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));

        checkEncodePassword(login.getPassword(), corporateEntity.getPassword(), passwordEncoder);
        checkDelete(corporateEntity.getDeyYN());

        final String[] tokens = generateToken(corporateEntity);
        redisService.createData(corporateEntity.getIdentity(), tokens[1], REFRESH_EXPIRE);

        return ResponseUsers.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    /**
     * Token Create
     * @param corporateEntity : login corporate
     * @return : accessToken, refreshToken
     */
    private String[] generateToken(CorporateEntity corporateEntity){
        final String accessToken = jwtProvider.createAccessToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
        final String refreshToken = jwtProvider.createRefreshToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());

        return new String[]{accessToken, refreshToken};
    }

    /**
     * 기업 회원 정보 조회
     * @return Responseusers.READ_CORPORATE : corporate info
     */
    public ResponseUsers.READ_CORPORATE getCorporate() {
        final CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());

        return CorporateEntity.of(corporateEntity);
    }

    /**
     * 본인 기업 정보 세부 조회
     * @return ResposneUsers.READ_DETAIL : address, email, phoneNumber
     */
    @Override
    public ResponseUsers.READ_USER_DETAIL getUserDetail() {
        final CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());

        return ResponseUsers.READ_USER_DETAIL.builder()
                .address(corporateEntity.getBusinessAddress())
                .email(corporateEntity.getBusinessEmail())
                .phoneNumber(corporateEntity.getBusinessPhoneNumber())
                .build();
    }

    /**
     * 회사 기본 정보 수정
     * @param updateCorporate : update info
     */
    @Transactional
    public void updateCorporate(UpdateUsers.UPDATE_CORPORATE updateCorporate) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        corporateEntity.updateInfo(updateCorporate);

        corporateRepository.save(corporateEntity);
    }

    /**
     * 본인 비밀번호 수정
     * @param updatePassword : update pw info
     */
    @Override
    @Transactional
    public void updatePassword(UpdateUsers.UPDATE_PASSWORD updatePassword) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(updatePassword.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);
        checkPassword(updatePassword.getNewPassword(), updatePassword.getNewCheckPassword());

        corporateEntity.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        corporateRepository.save(corporateEntity);
    }

    /**
     * Email update service
     * @param updateEmail : update email info
     */
    @Override
    @Transactional
    public void updateEmail(UpdateUsers.UPDATE_EMAIL updateEmail) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(updateEmail.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);

        corporateEntity.updateEmail(updateEmail.getEmail());
        corporateRepository.save(corporateEntity);
    }

    /**
     * Phone update service
     * @param updatePhoneNumber : update phone number info
     */
    @Override
    @Transactional
    public void updatePhoneNumber(UpdateUsers.UPDATE_PHONE_NUMBER updatePhoneNumber) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(updatePhoneNumber.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);

        corporateEntity.updatePhone(new PhoneNumber(updatePhoneNumber.getNewPhoneNumber()));
        corporateRepository.save(corporateEntity);
    }

    /**
     * Address update service
     * @param updateAddress : update address info
     */
    @Override
    @Transactional
    public void updateAddress(UpdateUsers.UPDATE_ADDRESS updateAddress) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(updateAddress.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);

        corporateEntity.updateAddress(updateAddress);
        corporateRepository.save(corporateEntity);
    }

    /**
     * 회원 탈퇴
     * @param delete : delete info
     */
    @Override
    @Transactional
    public void deleteUser(UpdateUsers.DELETE delete) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();

        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(delete.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);

        corporateEntity.deleted();
        corporateRepository.save(corporateEntity);
    }

    /**
     * 탈퇴 회원 복구
     * @param restore : restore info
     */
    @Override
    @Transactional
    public void restoreUser(UpdateUsers.RESTORE restore) {
        CorporateEntity corporateEntity = corporateRepository.findByIdentity(restore.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));

        checkRestore(corporateEntity.getDeyYN());
        checkEncodePassword(restore.getOriginalPassword(), corporateEntity.getPassword(), passwordEncoder);

        corporateEntity.restored();
        corporateRepository.save(corporateEntity);
    }

    /**
     * ID duplicated check
     * @param identity : user id
     */
    @Override
    public void isIdentity(String identity) {
        if(corporateRepository.existsByIdentity(identity)){
            throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
        }
    }

    /**
     * Phone duplicated check
     * @param phoneNumber : user phone number
     */
    @Override
    public void isPhoneNumber(String phoneNumber) {
        if(corporateRepository.existsByBusinessPhoneNumber(new PhoneNumber(phoneNumber))) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_PHONE);
        }
    }

    /**
     * Email duplicated check
     * @param email : user email
     */
    @Override
    public void isEmail(String email) {
        if(corporateRepository.existsByBusinessEmail(email)) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Override
    public Boolean canHandle(UserType userType) {
        return userType.equals(UserType.CORPORATE);
    }
}
