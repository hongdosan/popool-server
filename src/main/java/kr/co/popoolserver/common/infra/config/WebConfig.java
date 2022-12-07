package kr.co.popoolserver.common.infra.config;

import kr.co.popoolserver.common.infra.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] AUTH_ARR = {
            "/swagger/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "favicon.ico"
    };

    private static final String[] USER_AUTH_ARR = {
            "/users/**/signUp",
            "/users/**/login",
            "/users/reCreate",
            "/users/refresh-token",
            "/corporates/refresh-token"
    };

    private static final String[] S3_AUTH_ARR = {
            "/image",
            "/video",
            "/s3/**",
            "/s3-image/**",
            "/s3-video/**",
            "/s3-image-info/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(USER_AUTH_ARR)
                .excludePathPatterns(S3_AUTH_ARR)
                .excludePathPatterns(AUTH_ARR);
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }
}
