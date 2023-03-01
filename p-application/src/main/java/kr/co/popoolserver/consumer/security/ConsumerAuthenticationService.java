package kr.co.popoolserver.consumer.security;

import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.error.exception.NotFoundException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.provider.JwtProvider;
import kr.co.popoolserver.service.RedisService;
import kr.co.popoolserver.persistence.entity.CorporateEntity;
import kr.co.popoolserver.persistence.entity.UserEntity;
import kr.co.popoolserver.persistence.repository.CorporateRepository;
import kr.co.popoolserver.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConsumerAuthenticationService {

    private final RedisService redisService;

    private final UserRepository userRepository;

    private final CorporateRepository corporateRepository;

    private final JwtProvider jwtProvider;

    /**
     * RefreshToken 을 이용하여 User 의 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createUserAccessToken(String refreshToken){
        UserEntity userEntity = findUserByToken(refreshToken);
        redisService.checkValue(refreshToken, redisService.getValue(userEntity.getIdentity()));

        return jwtProvider.createAccessToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());
    }

    /**
     * RefreshToken 을 이용하여 Corporate 의 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createCorporateAccessToken(String refreshToken){
        CorporateEntity corporateEntity = findCorporateByToken(refreshToken);
        redisService.checkValue(refreshToken, redisService.getValue(corporateEntity.getIdentity()));

        return jwtProvider.createAccessToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
    }

    /**
     * 토큰을 통해 UserEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 UserEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public UserEntity findUserByToken(String token){
        return userRepository.findByIdentity(jwtProvider.findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIL_INVALID_TOKEN.getMessage()));
    }

    /**
     * 토큰을 통해 CorporateEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 UserEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public CorporateEntity findCorporateByToken(String token){
        return corporateRepository.findByIdentity(jwtProvider.findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIL_INVALID_TOKEN.getMessage()));
    }

    /**
     * 토큰을 통해서 Authentication 객체를 만들어내는 메서드
     * @param token 토큰
     * @return 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체
     */
    public Authentication getAuthentication(String token){
        final String identity = jwtProvider.findIdentityByToken(token);
        final String userRole = jwtProvider.findRoleByToken(token);
        UserDetails userDetails = new User(identity, "", getAuthorities(UserRole.of(userRole)));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Set<? extends GrantedAuthority> getAuthorities(UserRole userRole) {
        Set<GrantedAuthority> set = new HashSet<>();
        if(userRole.equals(UserRole.ROLE_CORPORATE)) set.add(new SimpleGrantedAuthority("ROLE_CORPORATE"));
        else set.add(new SimpleGrantedAuthority("ROLE_USER"));

        return set;
    }
}
