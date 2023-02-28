package kr.co.popoolserver.infra.config;

import kr.co.popoolserver.infra.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECOND = 3600;

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

    private static final String[] ADMIN_AUTH_ARR = {
            "/admin/create",
            "/admin/login",
            "/admin/refresh-token"
    };

    private static final String[] USER_AUTH_ARR = {
            "/users/**/create",
            "/users/**/login",
            "/users/restore",
            "/users/refresh-token"
    };

    private static final String[] PRODUCT_AUTH_ARR = {
            "/products",
            "/products/detail"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(ADMIN_AUTH_ARR)
                .excludePathPatterns(USER_AUTH_ARR)
                .excludePathPatterns(PRODUCT_AUTH_ARR)
                .excludePathPatterns(AUTH_ARR);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECOND);
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }
}
