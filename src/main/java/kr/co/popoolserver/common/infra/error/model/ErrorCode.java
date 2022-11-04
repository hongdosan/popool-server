package kr.co.popoolserver.common.infra.error.model;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //common
    SUCCESS_NULL("실행이 성공했고, 응답 데이터는 없습니다.", 2000),
    SUCCESS_VALUE("실행이 성공했고, 응답 데이터가 있습니다.", 2001),

    FAIL_NULL("실행이 실패했고, 응답 데이터는 없습니다.", 4000),
    FAIL_EXPIRE("실행이 실패했고, 원인은 토큰 만료입니다.", 4001),

    //user
    WRONG_IDENTITY("존재하지 않는 아이디입니다.", 400),
    WRONG_PASSWORD("비밀번호를 다시 확인해주세요", 400),

    DUPLICATED_ID("중복된 아이디를 사용할 수 없습니다.", 400),
    DUPLICATED_PHONE("중복된 전화번호를 사용할 수 없습니다.", 400),
    DUPLICATED_EMAIL("중복된 이메일을 사용할 수 없습니다.", 400),

    DELETED_USER("탈퇴한 회원입니다.", 400);

    //TODO: corporate, payment, product, inventory, career, score, grade Error Enum

    private String message;
    private int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
