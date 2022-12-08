package kr.co.popoolserver.error.exception;

import kr.co.popoolserver.error.model.ErrorCode;

public class FileUploadFailedException extends BusinessLogicException{
    public FileUploadFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
