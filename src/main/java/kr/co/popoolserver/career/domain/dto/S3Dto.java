package kr.co.popoolserver.career.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

public class S3Dto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class DOWNLOAD {
        private byte[] bytes;

        private HttpHeaders httpHeaders;
    }
}