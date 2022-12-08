package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class EmptyFileException extends BusinessLogicException{
    public EmptyFileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
