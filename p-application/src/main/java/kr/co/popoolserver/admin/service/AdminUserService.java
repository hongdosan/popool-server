package kr.co.popoolserver.admin.service;

import kr.co.popoolserver.dtos.RequestAdmin;
import kr.co.popoolserver.dtos.ResponseAdmin;
import kr.co.popoolserver.entitiy.AdminEntity;
import kr.co.popoolserver.enums.AdminType;
import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.DuplicatedException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.repository.AdminRepository;
import kr.co.popoolserver.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService implements AdminCommonService{

    private final AdminRepository adminRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;

    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    /**
     * admin login
     * @param loginAdmin : ID, PW
     * @return AccessToken, RefreshToken
     */
    public ResponseAdmin.TOKEN_ADMIN login(RequestAdmin.LOGIN_ADMIN loginAdmin) {
        final AdminEntity adminEntity = adminRepository.findByIdentity(loginAdmin.getIdentity())
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.WRONG_IDENTITY));

        checkEncodePassword(loginAdmin.getPassword(), adminEntity.getPassword(), passwordEncoder);
        checkAdminRole(adminEntity.getUserRole());

        final String[] tokens = generateToken(adminEntity);
        redisService.createData(adminEntity.getIdentity(), tokens[1], REFRESH_EXPIRE);

        return ResponseAdmin.TOKEN_ADMIN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    private String[] generateToken(AdminEntity adminEntity){
        final String accessToken = jwtProvider.createAccessToken(adminEntity.getIdentity(), adminEntity.getUserRole(), adminEntity.getName());
        final String refreshToken = jwtProvider.createRefreshToken(adminEntity.getIdentity(), adminEntity.getUserRole(), adminEntity.getName());

        return new String[]{accessToken, refreshToken};
    }

    /**
     * admin create
     * @param createAdmin : user info
     * @exception DuplicatedException : ID, Phone Duplicated
     * @exception BusinessLogicException : PW Check
     */
    @Transactional
    public void createAdmin(RequestAdmin.CREATE_ADMIN createAdmin) {
        checkIdentity(createAdmin.getIdentity());
        checkPassword(createAdmin.getPassword(), createAdmin.getCheckPassword());

        final AdminEntity adminEntity = AdminEntity.of(createAdmin, passwordEncoder);
        adminRepository.save(adminEntity);
    }

    public List<ResponseAdmin.READ_ADMIN> getAllAdmin() {
        final AdminEntity adminEntity = AdminThreadLocal.get();
        checkAdminRole(adminEntity.getUserRole());

        return adminRepository.findAll().stream()
                .map(AdminEntity::toDto).collect(Collectors.toList());
    }

    /**
     * 회원 탈퇴
     * @param delete
     */
    @Transactional
    public void deleteAdmin(RequestAdmin.DELETE_ADMIN deleteAdmin) {
        final AdminEntity adminEntity = AdminThreadLocal.get();

        checkEncodePassword(deleteAdmin.getOriginalPassword(), adminEntity.getPassword(), passwordEncoder);
        checkAdminRole(adminEntity.getUserRole());

        adminRepository.delete(adminEntity);
    }

    private void checkIdentity(String identity) {
        if(adminRepository.existsByIdentity(identity)) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
        }
    }

    @Override
    public Boolean canHandle(AdminType adminType) {
        return adminType.equals(AdminType.ADMIN);
    }
}
