package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class EmptyFileException extends BusinessLogicException{
    public EmptyFileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
