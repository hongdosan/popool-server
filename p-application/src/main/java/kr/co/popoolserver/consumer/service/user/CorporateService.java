package kr.co.popoolserver.consumer.service.user;

import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.dtos.request.CreateUsers;
import kr.co.popoolserver.dtos.response.ResponseUsers;
import kr.co.popoolserver.entity.user.CorporateEntity;
import kr.co.popoolserver.dtos.CorporateDto;
import kr.co.popoolserver.dtos.UserCommonDto;
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

        String[] tokens = generateToken(corporateEntity);
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
        String accessToken = jwtProvider.createAccessToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
        String refreshToken = jwtProvider.createRefreshToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());

        return new String[]{accessToken, refreshToken};
    }

    /**
     * 기업 회원 정보 조회
     * @return Responseusers.READ_CORPORATE : corporate info
     */
    public ResponseUsers.READ_CORPORATE getCorporate() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());

        return CorporateEntity.of(corporateEntity);
    }

    /**
     * 본인 기업 정보 세부 조회
     * @return ResposneUsers.READ_DETAIL : address, email, phoneNumber
     */
    @Override
    public ResponseUsers.READ_DETAIL getUserDetail() {
        CorporateEntity corporateEntity = CorporateThreadLocal.get();
        checkDelete(corporateEntity.getDeyYN());

        return ResponseUsers.READ_DETAIL.builder()
                .address(corporateEntity.getBusinessAddress())
                .email(corporateEntity.getBusinessEmail())
                .phoneNumber(corporateEntity.getBusinessPhoneNumber())
                .build();
    }

//    /**
//     * 회사 기본 정보 수정 (이름, 사업자 번호, 사업자 명, 대표 명)
//     * @param update
//     */
//    @Transactional
//    public void updateCorporate(CorporateDto.UPDATE update) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        corporateEntity.updateInfo(update);
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * 본인 비밀번호 수정
//     * @param password
//     */
//    @Override
//    @Transactional
//    public void updatePassword(UserCommonDto.UPDATE_PASSWORD password) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        checkEncodePassword(password.getOriginalPassword(), corporateEntity.getPassword());
//        checkPassword(password.getNewPassword(), password.getNewCheckPassword());
//        corporateEntity.updatePassword(passwordEncoder.encode(password.getNewPassword()));
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * Email update service
//     * @param email
//     */
//    @Override
//    @Transactional
//    public void updateEmail(UserCommonDto.UPDATE_EMAIL email) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        checkEncodePassword(email.getOriginalPassword(), corporateEntity.getPassword());
//        checkEmail(email.getEmail());
//        corporateEntity.updateEmail(email.getEmail());
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * Phone update service
//     * @param phone
//     */
//    @Override
//    @Transactional
//    public void updatePhone(UserCommonDto.UPDATE_PHONE phone) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        checkEncodePassword(phone.getOriginalPassword(), corporateEntity.getPassword());
//        checkPhoneNumber(phone.getNewPhoneNumber());
//        corporateEntity.updatePhone(new PhoneNumber(phone.getNewPhoneNumber()));
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * Address update service
//     * @param address
//     */
//    @Override
//    @Transactional
//    public void updateAddress(UserCommonDto.UPDATE_ADDRESS address) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        checkEncodePassword(address.getOriginalPassword(), corporateEntity.getPassword());
//        corporateEntity.updateAddress(address);
//        corporateRepository.save(corporateEntity);
//    }
//

//
//    /**
//     * 회원 탈퇴
//     * @param delete
//     */
//    @Transactional
//    public void deleteCorporate(CorporateDto.DELETE delete) {
//        CorporateEntity corporateEntity = CorporateThreadLocal.get();
//        checkDelete(corporateEntity.getDeyYN());
//        checkEncodePassword(delete.getOriginalPassword(), corporateEntity.getPassword());
//        corporateEntity.deleted();
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * 탈퇴 회원 복구
//     * @param reCreate
//     */
//    @Transactional
//    public void reCreateCorporate(CorporateDto.RE_CREATE reCreate) {
//        CorporateEntity corporateEntity = corporateRepository.findByIdentity(reCreate.getIdentity())
//                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
//        checkReCreate(corporateEntity.getDeyYN());
//        checkEncodePassword(reCreate.getOriginalPassword(), corporateEntity.getPassword());
//        corporateEntity.reCreated();
//        corporateRepository.save(corporateEntity);
//    }
//
//    /**
//     * Redis에 저장된 RefreshToken 삭제
//     * @param identity
//     */
//    @Override
//    public void deleteRefreshToken(String identity){
//        redisService.deleteData(identity);
//    }
//

//
//    /**
//     * reCreate Check Service
//     * @param delYN
//     */
//    @Override
//    public void checkReCreate(String delYN) {
//        if(delYN.equals("N")) throw new BadRequestException("탈퇴되지 않은 기업 회원 입니다.");
//    }

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
