package kr.co.popoolserver.dtos;

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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CONVERT {
        private String fileUrl;

        private String fileName;

        private String fileExtension;

        private long fileSize;
    }
}
