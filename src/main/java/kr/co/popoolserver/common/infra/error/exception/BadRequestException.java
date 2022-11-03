package kr.co.popoolserver.common.infra.error.exception;

public class BadRequestException extends BusinessLoginException{

    public BadRequestException(String message) {
        super(String.format("Bad Request : %s", message));
    }
}