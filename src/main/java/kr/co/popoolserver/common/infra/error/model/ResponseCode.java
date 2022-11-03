package kr.co.popoolserver.common.infra.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(1),
    FAIL(2),
    TOKEN_EXPIRED(3);

    int code;

    public static ResponseCode of(int code){
        return Arrays.stream(ResponseCode.values())
                .filter(responseCode -> responseCode.getCode() == code)
                .findFirst().orElseThrow(() -> new RuntimeException("응답 코드를 찾을 수 없습니다."));
    }
}
