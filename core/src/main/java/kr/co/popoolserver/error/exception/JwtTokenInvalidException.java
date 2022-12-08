package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{

    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}
