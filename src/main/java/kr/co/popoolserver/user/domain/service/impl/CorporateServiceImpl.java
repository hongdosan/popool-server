package kr.co.popoolserver.user.domain.service.impl;

import kr.co.popoolserver.common.domain.PhoneNumber;
import kr.co.popoolserver.common.domain.enums.ServiceName;
import kr.co.popoolserver.common.infra.error.exception.BadRequestException;
import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.DuplicatedException;
import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import kr.co.popoolserver.common.infra.interceptor.CorporateThreadLocal;
import kr.co.popoolserver.common.infra.jwt.JwtProvider;
import kr.co.popoolserver.user.domain.dto.CorporateDto;
import kr.co.popoolserver.user.domain.dto.UserCommonDto;
import kr.co.popoolserver.user.domain.entity.CorporateEntity;
import kr.co.popoolserver.user.domain.service.CorporateService;
import kr.co.popoolserver.user.repository.CorporateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorporateServiceImpl implements CorporateService {

    private final CorporateRepository corporateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * login service
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     */
    @Override
    public UserCommonDto.TOKEN login(UserCommonDto.LOGIN login) {
        CorporateEntity corporateEntity = corporateRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkEncodePassword(login.getPassword(), corporateEntity.getPassword());
        checkDelete(corporateEntity.getDeyYN());
        String[] tokens = generateToken(corporateEntity);

        return UserCommonDto.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    /**
     * Token Create
     * @param corporateEntity
     * @return
     */
    private String[] generateToken(CorporateEntity corporateEntity){
        String accessToken = jwtProvider.createAccessToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
        String refreshToken = jwtProvider.createRefreshToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
        return new String[]{accessToken, refreshToken};
    }


    /**
     * signUp Service
     * @param create : corporate info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Override
    public void signUp(CorporateDto.CREATE create) {
        checkIdentity(create.getIdentity());
        checkPassword(create.getPassword(), create.getCheckPassword());
        checkPhoneNumber(create.getBusinessPhoneNumber());

        final CorporateEntity corporateEntity = CorporateEntity.of(create, passwordEncoder);
        corporateRepository.save(corporateEntity);
    }

    @Override
    public void updateCorporate(CorporateDto.UPDATE update) {
        //TODO : corporate Service
    }

    /**
     * 본인 비밀번호 수정
     * @param password
     */
    @Override
    public void updatePassword(UserCommonDto.UPDATE_PASSWORD password) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(password.getOriginalPassword(), corporateEntity.getPassword());
        checkPassword(password.getNewPassword(), password.getNewCheckPassword());
        corporateEntity.updatePassword(passwordEncoder.encode(password.getNewPassword()));
        corporateRepository.save(corporateEntity);
    }

    /**
     * Email update service
     * @param email
     */
    @Override
    public void updateEmail(UserCommonDto.UPDATE_EMAIL email) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(email.getOriginalPassword(), corporateEntity.getPassword());
        checkEmail(email.getEmail());
        corporateEntity.updateEmail(email.getEmail());
        corporateRepository.save(corporateEntity);
    }

    /**
     * Phone update service
     * @param phone
     */
    @Override
    public void updatePhone(UserCommonDto.UPDATE_PHONE phone) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(phone.getOriginalPassword(), corporateEntity.getPassword());
        checkPhoneNumber(phone.getNewPhoneNumber());
        corporateEntity.updatePhone(new PhoneNumber(phone.getNewPhoneNumber()));
        corporateRepository.save(corporateEntity);
    }

    /**
     * Address update service
     * @param address
     */
    @Override
    public void updateAddress(UserCommonDto.UPDATE_ADDRESS address) {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        checkEncodePassword(address.getOriginalPassword(), corporateEntity.getPassword());
        corporateEntity.updateAddress(address);
        corporateRepository.save(corporateEntity);
    }

    @Override
    public CorporateDto.READ getCorporate() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        return CorporateEntity.of(corporateEntity);
    }

    /**
     * 본인 주소 조회
     * @return READ_ADDRESS
     */
    @Override
    public UserCommonDto.READ_ADDRESS getAddress() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        return UserCommonDto.READ_ADDRESS.builder()
                .address(corporateEntity.getBusinessAddress())
                .build();
    }

    /**
     * 본인 메일 조회
     * @return READ_EMAIL
     */
    @Override
    public UserCommonDto.READ_EMAIL getEmail() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        return UserCommonDto.READ_EMAIL.builder()
                .email(corporateEntity.getBusinessEmail())
                .build();
    }

    /**
     * 본인 번호 조회
     * @return READ_PHONE
     */
    @Override
    public UserCommonDto.READ_PHONE getPhone() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());
        return UserCommonDto.READ_PHONE.builder()
                .phoneNumber(corporateEntity.getBusinessPhoneNumber())
                .build();
    }


    @Override
    public void deleteCorporate(CorporateDto.DELETE delete) {
        //TODO : corporate Service
    }

    @Override
    public void reCreateCorporate(CorporateDto.RE_CREATE reCreate) {
        //TODO : corporate Service
    }

    /**
     * ID duplicated check
     * @param identity
     */
    @Override
    public void checkIdentity(String identity) {
        if(corporateRepository.existsByIdentity(identity)) throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
    }

    /**
     * Phone duplicated check
     * @param phoneNumber
     */
    @Override
    public void checkPhoneNumber(String phoneNumber) {
        if(corporateRepository.existsByBusinessPhoneNumber(new PhoneNumber(phoneNumber))) throw new DuplicatedException(ErrorCode.DUPLICATED_PHONE);
    }

    /**
     * Email duplicated check
     * @param email
     */
    @Override
    public void checkEmail(String email) {
        if(corporateRepository.existsByBusinessEmail(email)) throw new DuplicatedException(ErrorCode.DUPLICATED_EMAIL);
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
        if(delYN.equals("N")) throw new BadRequestException("탈퇴되지 않은 기업 회원 입니다.");
    }

    @Override
    public Boolean canHandle(ServiceName serviceName) {
        return serviceName.equals(ServiceName.CORPORATE);
    }
}
