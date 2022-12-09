package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class MaxUploadSizeExceededException extends BusinessLogicException{
    public MaxUploadSizeExceededException(ErrorCode errorCode) {
        super(errorCode);
    }
}
