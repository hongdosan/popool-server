package kr.co.popoolserver.common.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.popoolserver.common.domain.enums.UserRole;
import kr.co.popoolserver.common.infra.error.exception.UserDefineException;
import kr.co.popoolserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("$jwt.secret")
    private String SECRET_KEY;

    final String USER_IDENTITY = "identtiy";
    final String USER_ROLE = "user_role";
    final String USER_NAME = "name";

    private final long ACCESS_EXPIRE = 1000*60*30;
    private final long REFRESH_EXPIRE = 1000*60*60*24*7;

    private final UserRepository userRepository;

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

}
