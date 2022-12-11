package kr.co.popoolserver.infrastructure.jwt;

import io.jsonwebtoken.*;
import kr.co.popoolserver.enums.UserRole;
import kr.co.popoolserver.error.exception.JwtTokenInvalidException;
import kr.co.popoolserver.error.exception.UserDefineException;
import kr.co.popoolserver.error.model.ErrorCode;
import kr.co.popoolserver.service.RedisService;
import kr.co.popoolserver.user.domain.entity.CorporateEntity;
import kr.co.popoolserver.user.domain.entity.UserEntity;
import kr.co.popoolserver.user.repository.CorporateRepository;
import kr.co.popoolserver.user.repository.UserRepository;
import kr.co.popoolserver.error.exception.JwtTokenExpiredException;
import kr.co.popoolserver.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("$jwt.secret")
    private String SECRET_KEY;

    final String USER_IDENTITY = "identity";
    final String USER_ROLE = "user_role";
    final String USER_NAME = "name";

    private final long ACCESS_EXPIRE = 1000*60*30;
    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    private final RedisService redisService;
    private final UserRepository userRepository;
    private final CorporateRepository corporateRepository;

    /**
     * 시크릿 키를 Base64로 인코딩
     */
    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 키 변환을 위한 key 를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey(){
        try{
            return SECRET_KEY.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage());
        }
    }

    /**
     * 사용자 정보를 통해 Claims 객체를 생성하는 메소드
     * @param identity : 사용자 아이디
     * @param userRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자 정보를 포함한 Claims 객체
     */
    private Claims generateClaims(String identity, UserRole userRole, String name){
        Claims claims = Jwts.claims();
        claims.put(USER_IDENTITY, identity);
        claims.put(USER_ROLE, userRole);
        claims.put(USER_NAME, name);
        return claims;
    }

    /**
     * 사용자 정보를 통해 AccessToken을 생성하는 메소드
     * @param identity : 사용자 아이디
     * @param userRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자의 AccessToken
     */
    public String createAccessToken(String identity, UserRole userRole, String name){
        Date issueDate = new Date();    //토큰 발행 시각.
        Date expireDate = new Date();   //토큰 유효 시각.
        expireDate.setTime(issueDate.getTime() + ACCESS_EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(generateClaims(identity, userRole, name))
                .setIssuedAt(issueDate)
                .setSubject("AccessToken")
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * RefreshToken 을 이용하여 User 의 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createUserAccessToken(String refreshToken){
        UserEntity userEntity = findUserByToken(refreshToken);
        redisService.checkValue(refreshToken, redisService.getValue(userEntity.getIdentity()));

        return createAccessToken(userEntity.getIdentity(), userEntity.getUserRole(), userEntity.getName());
    }

    /**
     * RefreshToken 을 이용하여 Corporate 의 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createCorporateAccessToken(String refreshToken){
        CorporateEntity corporateEntity = findCorporateByToken(refreshToken);
        redisService.checkValue(refreshToken, redisService.getValue(corporateEntity.getIdentity()));

        return createAccessToken(corporateEntity.getIdentity(), corporateEntity.getUserRole(), corporateEntity.getName());
    }

    /**
     * 사용자 정보를 통해 RefreshToken 을 만드는 메서드
     * @param identity : 사용자 아이디
     * @param userRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자의 RefreshToken
     */
    public String createRefreshToken(String identity, UserRole userRole, String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + REFRESH_EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(generateClaims(identity, userRole, name))
                .setIssuedAt(issueDate)
                .setSubject("RefreshToken")
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * 토큰의 유효성을 판단하는 메소드
     * @param token : 토큰
     * @return 토큰이 만료되었는지에 대한 불리언 값
     * @exception ExpiredJwtException 토큰이 만료되었을 경우에 발생하는 예외
     */
    public boolean isUsable(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException(ErrorCode.FAIL_EXPIRE);
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT Claims");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_CLAIMS);
        }
    }

    /**
     * 헤더에 있는 토큰을 추출하는 메서드
     * 평소에는 AccessToken을 담아서 주고 받다가 만료가 되었다는 예외가 발생하면 그때 Refresh만
     * @param request 사용자의 요청
     * @return AccessToken 과 RefreshToken 을 담은 객체를 Optional로 감싼 데이터
     */
    public Optional<String> resolveToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("token"));
    }

    /**
     * 토큰을 통해서 Authentication 객체를 만들어내는 메서드
     * @param token 토큰
     * @return 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체
     */
    public Authentication getAuthentication(String token){
        final String identity = findIdentityByToken(token);
        final String userRole = findRoleByToken(token);
        UserDetails userDetails = new User(identity, "", getAuthorities(UserRole.of(userRole)));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Set<? extends GrantedAuthority> getAuthorities(UserRole userRole) {
        Set<GrantedAuthority> set = new HashSet<>();
        if(userRole.equals(UserRole.ROLE_CORPORATE)) set.add(new SimpleGrantedAuthority("ROLE_CORPORATE"));
        else set.add(new SimpleGrantedAuthority("ROLE_USER"));

        return set;
    }

    /**
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findIdentityByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(USER_IDENTITY);
    }

    /**
     * 토큰을 이용하여 사용자 권한을 찾는 메서드
     * @param token 토큰
     * @return 사용자의 권한
     */
    public String findRoleByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(USER_ROLE);
    }


    /**
     * 일반 회원일 시 예외 발생
     * @param userRole
     */
    public void checkUserRole(UserRole userRole){
        if(userRole.equals(UserRole.ROLE_USER)) throw new UserDefineException(ErrorCode.FAIL_USER_ROLE);
    }

    /**
     * 토큰을 통해 UserEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 UserEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public UserEntity findUserByToken(String token){
        return userRepository.findByIdentity(findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIL_INVALID_TOKEN.getMessage()));
    }

    /**
     * 토큰을 통해 CorporateEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 UserEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public CorporateEntity findCorporateByToken(String token){
        return corporateRepository.findByIdentity(findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIL_INVALID_TOKEN.getMessage()));
    }
}
