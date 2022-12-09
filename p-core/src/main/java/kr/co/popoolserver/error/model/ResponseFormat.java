package kr.co.popoolserver.error.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseFormat<T> {

    private boolean result;

    private T data;

    private String message;
    private int status;

    public static ResponseFormat ok(){
        return ResponseFormat.builder()
                .result(true)
                .data(null)
                .message(ErrorCode.SUCCESS_NULL.getMessage())
                .status(ErrorCode.SUCCESS_NULL.getStatus())
                .build();
    }

    public static <T> ResponseFormat ok(T data){
        return ResponseFormat.builder()
                .result(true)
                .data(data)
                .message(ErrorCode.SUCCESS_VALUE.getMessage())
                .status(ErrorCode.SUCCESS_VALUE.getStatus())
                .build();
    }

    public static ResponseFormat fail(String message){
        return ResponseFormat.builder()
                .result(false)
                .data(null)
                .message(ErrorCode.FAIL_NULL.getMessage())
                .status(ErrorCode.FAIL_NULL.getStatus())
                .build();
    }

    public static ResponseFormat expire(){
        return ResponseFormat.builder()
                .result(false)
                .data(null)
                .message(ErrorCode.FAIL_EXPIRE.getMessage())
                .status(ErrorCode.FAIL_EXPIRE.getStatus())
                .build();
    }
}
