package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode){
        super(errorCode);
    }
}
