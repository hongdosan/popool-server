package kr.co.popoolserver.common.infra.handler;

import kr.co.popoolserver.common.infra.error.exception.BusinessLogicException;
import kr.co.popoolserver.common.infra.error.exception.MaxUploadSizeExceededException;
import kr.co.popoolserver.common.infra.error.exception.UserDefineException;
import kr.co.popoolserver.common.infra.error.model.ErrorResponse;
import kr.co.popoolserver.common.infra.error.model.ResponseFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessLogicException.class, RuntimeException.class})
    public ResponseEntity handlerRuntimeException(RuntimeException e){
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity<ErrorResponse> handlerUserDefineException(UserDefineException e){
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }
}
