package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class DuplicatedException extends BusinessLogicException {
    public DuplicatedException(ErrorCode errorCode){
        super(errorCode);
    }
}
