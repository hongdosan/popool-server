package kr.co.popoolserver.infra.handler;

import kr.co.popoolserver.error.exception.BusinessLogicException;
import kr.co.popoolserver.error.exception.MaxUploadSizeExceededException;
import kr.co.popoolserver.error.exception.UserDefineException;
import kr.co.popoolserver.error.model.ErrorResponse;
import kr.co.popoolserver.error.model.ResponseFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //RestController 어노테이션이 붙은 모든 API에서 발생한 모든 예외들을 가로채는 역할을 하게 된다.
public class GlobalExceptionHandler {

    //모든 예외 -> RuntimeException -> default 에외
    //BusinessLogicExcepton -> 사용자 지정 예외 메시지
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
