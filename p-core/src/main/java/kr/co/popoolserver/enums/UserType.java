package kr.co.popoolserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {
    USER,
    CORPORATE;

    public static UserType of(String userType){
        return Arrays.stream(UserType.values())
                .filter(g -> g.toString().equalsIgnoreCase(userType))
                .findAny().orElseThrow(() -> new RuntimeException("해당 회원 타입은 존재하지 않습니다."));
    }
}
