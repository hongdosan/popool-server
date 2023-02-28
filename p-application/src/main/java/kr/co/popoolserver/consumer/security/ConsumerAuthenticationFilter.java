package kr.co.popoolserver.consumer.security;

import kr.co.popoolserver.error.exception.JwtTokenInvalidException;
import kr.co.popoolserver.error.exception.JwtTokenExpiredException;
import kr.co.popoolserver.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConsumerAuthenticationFilter extends OncePerRequestFilter {

    private final ConsumerAuthenticationService consumerAuthenticationService;

    private final JwtProvider jwtProvider;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final Optional<String> token = jwtProvider.resolveToken(request);
        try {
            if(token.isPresent() && jwtProvider.isUsable(token.get())){
                Authentication authentication = consumerAuthenticationService.getAuthentication(token.get());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (JwtTokenExpiredException | JwtTokenInvalidException e){
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
