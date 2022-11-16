package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class MaxUploadSizeExceededException extends BusinessLogicException{
    public MaxUploadSizeExceededException(ErrorCode errorCode) {
        super(errorCode);
    }
}
