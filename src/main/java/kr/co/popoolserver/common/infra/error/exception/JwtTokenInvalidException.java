package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{

    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}
