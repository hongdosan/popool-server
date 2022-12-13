package kr.co.popoolserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {

    ROLE_USER("일반회원"),
    ROLE_CORPORATE("기업회원"),
    ROLE_ADMIN("관리자");

    private String role;

    public static UserRole of(String role){
        return Arrays.stream(UserRole.values())
                .filter(r -> r.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(() -> new RuntimeException("해당 권한은 존재하지 않습니다."));
    }
}
