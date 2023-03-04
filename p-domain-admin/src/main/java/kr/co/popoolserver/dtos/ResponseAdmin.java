package kr.co.popoolserver.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class ResponseAdmin {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TOKEN_ADMIN {
        @ApiModelProperty(example = "AccessToken")
        private String accessToken;

        @ApiModelProperty(example = "RefreshToken")
        private String refreshToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ_ADMIN {
        @ApiModelProperty(example = "HongDosan")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
    }
}
