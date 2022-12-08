package kr.co.popoolserver.error.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseFormat<T> {

    @ApiModelProperty(example = "true or false")
    private boolean result;

    @ApiModelProperty(example = "반환될 데이터")
    private T data;

    @ApiModelProperty(example = "2000(성공, NULL), 2001(성공, VALUE)")
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
