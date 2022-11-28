package kr.co.popoolserver.common.infra.error.model;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //common
    SUCCESS_NULL("실행이 성공했고, 응답 데이터는 없습니다.", 2000),
    SUCCESS_VALUE("실행이 성공했고, 응답 데이터가 있습니다.", 2001),

    FAIL_NULL("응답이 실패했고, 응답 데이터는 없습니다.", 4000),
    FAIL_EXPIRE("응답이 실패했고, 원인은 토큰 만료입니다.", 4001),

    FAIL_INVALID_TOKEN("응답이 실패했고, 원인은 Invalid JWT Token 입니다.", 4002),
    FAIL_INVALID_SIGNATURE("응답이 실패했고, 원인은 Invalid JWT Signature 입니다.", 4002),
    FAIL_INVALID_CLAIMS("응답이 실패했고, 원인은 Invalid JWT Claims(EMPTY) 입니다.", 4002),

    NOT_SERVICE("해당 서비스는 존재하지 않습니다.", 4003),

    //user
    WRONG_IDENTITY("존재하지 않는 아이디입니다.", 4100),
    WRONG_PASSWORD("비밀번호를 다시 확인해주세요", 4100),

    DUPLICATED_ID("중복된 아이디를 사용할 수 없습니다.", 4101),
    DUPLICATED_PHONE("중복된 전화번호를 사용할 수 없습니다.", 4101),
    DUPLICATED_EMAIL("중복된 이메일을 사용할 수 없습니다.", 4101),

    DELETED_USER("탈퇴한 회원입니다.", 4102),

    //career
    WRONG_CAREER("해당 유저가 작성한 이력서는 없습니다.", 4100),

    //careerFile
    FAIL_FILE_EMPTY("파일이 존재하지 않습니다.", 4100),
    FAIL_FILE_INVALID_CONTENT_TYPE("확장자가 없습니다.", 4100),

    FAIL_FILE_UPLOAD("파일 업로드 실패하였습니다.", 4101);

    //TODO: payment, product, inventory, score, grade Error Enum

    private String message;
    private int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
