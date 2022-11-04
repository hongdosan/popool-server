package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessLoginException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessLoginException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLoginException(String part, ErrorCode errorCode){
        super(part + " : " + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessLoginException(String message){
        super(message);
    }
}
