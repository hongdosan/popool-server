package kr.co.popoolserver.common.infra.interceptor;

import kr.co.popoolserver.common.infra.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProvider jwtProvider;
    Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "").trim();
        String userRole = jwtProvider.findRoleByToken(token);

        if(userRole.equals("ROLE_USER")){
            UserThreadLocal.set(jwtProvider.findUserByToken(token));
            logger.debug("User ThreadLocal Create");
        }else if(userRole.equals("ROLE_CORPORATE")){
            CorporateThreadLocal.set(jwtProvider.findCorporateByToken(token));
            logger.debug("Corporate ThreadLocal Create");
        }

        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           ModelAndView modelAndView) {
        threadLocalRemoveCheck();
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex){
        threadLocalRemoveCheck();
    }

    private void threadLocalRemoveCheck(){
        if(UserThreadLocal.get() == null && CorporateThreadLocal.get() == null) return;
        if(UserThreadLocal.get() != null){
            UserThreadLocal.remove();
            logger.debug("User ThreadLocal PostHandle Remove");
        }
        if(CorporateThreadLocal.get() != null){
            CorporateThreadLocal.remove();
            logger.debug("Corporate ThreadLocal PostHandle Remove");
        }
    }
}
