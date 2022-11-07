package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode){
        super(errorCode);
    }
}
