package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.entitiy.dto.AdminDto;
import kr.co.popoolserver.entitiy.AdminEntity;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.auth.AdminThreadLocal;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.AdminRepository;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    /**
     * login service
     * @param login : ID, PW
     * @return AccessToken, RefreshToken
     */
    public AdminDto.TOKEN login(AdminDto.LOGIN login) {
        AdminEntity adminEntity = adminRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));
        checkEncodePassword(login.getPassword(), adminEntity.getPassword());
        jwtProvider.checkAdminRole(adminEntity.getUserRole());

        String[] tokens = generateToken(adminEntity);
        redisService.createData(adminEntity.getIdentity(), tokens[1], REFRESH_EXPIRE);

        return AdminDto.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    /**
     * Token Create
     * @param adminEntity
     * @return
     */
    private String[] generateToken(AdminEntity adminEntity){
        String accessToken = jwtProvider.createAccessToken(adminEntity.getIdentity(), adminEntity.getUserRole(), adminEntity.getName());
        String refreshToken = jwtProvider.createRefreshToken(adminEntity.getIdentity(), adminEntity.getUserRole(), adminEntity.getName());
        return new String[]{accessToken, refreshToken};
    }

    /**
     * signUp Service
     * @param create : user info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Transactional
    public void signUp(AdminDto.CREATE create) {
        checkIdentity(create.getIdentity());
        checkPassword(create.getPassword(), create.getCheckPassword());

        final AdminEntity adminEntity = AdminEntity.of(create, passwordEncoder);
        adminRepository.save(adminEntity);
    }

    /**
     * 본인 회원 정보 조회
     * @return : AdminDto.READ
     */
    public AdminDto.READ getAdmin() {
        AdminEntity adminEntity = AdminThreadLocal.get();
        jwtProvider.checkAdminRole(adminEntity.getUserRole());
        return AdminEntity.of(adminEntity);
    }

    /**
     * 회원 탈퇴
     * @param delete
     */
    @Transactional
    public void deleteAdmin(AdminDto.DELETE delete) {
        AdminEntity adminEntity = AdminThreadLocal.get();
        jwtProvider.checkAdminRole(adminEntity.getUserRole());
        checkEncodePassword(delete.getOriginalPassword(), adminEntity.getPassword());
        adminRepository.delete(adminEntity);
    }

    /**
     * 권한 체크
     */
    public void checkAdmin(){
        AdminEntity adminEntity = AdminThreadLocal.get();
        jwtProvider.checkAdminRole(adminEntity.getUserRole());
    }

    /**
     * ID duplicated check
     * @param identity
     */
    private void checkIdentity(String identity) {
        if(adminRepository.existsByIdentity(identity)) throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
    }

    /**
     * PW check
     * @param password : user pw
     * @param checkPassword : check pw
     */
    private void checkPassword(String password, String checkPassword) {
        if(!password.equals(checkPassword)) throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
    }

    /**
     * Login PW Check
     * @param password
     * @param encodePassword
     */
    private void checkEncodePassword(String password, String encodePassword) {
        if(!passwordEncoder.matches(password, encodePassword)) throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
    }
}
