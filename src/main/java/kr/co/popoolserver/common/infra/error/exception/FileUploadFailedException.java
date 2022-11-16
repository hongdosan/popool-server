package kr.co.popoolserver.common.infra.error.exception;

import kr.co.popoolserver.common.infra.error.model.ErrorCode;

public class FileUploadFailedException extends BusinessLogicException{
    public FileUploadFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
