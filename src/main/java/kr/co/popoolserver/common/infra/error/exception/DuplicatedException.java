package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class DuplicatedException extends BusinessLogicException {
    public DuplicatedException(ErrorCode errorCode){
        super(errorCode);
    }
}
