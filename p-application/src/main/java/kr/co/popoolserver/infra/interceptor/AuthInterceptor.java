package kr.co.popoolserver.infra.interceptor;

import kr.co.popoolserver.admin.security.AdminAuthenticationService;
import kr.co.popoolserver.consumer.security.ConsumerAuthenticationService;
import kr.co.popoolserver.admin.security.AdminThreadLocal;
import kr.co.popoolserver.consumer.security.CorporateThreadLocal;
import kr.co.popoolserver.consumer.security.UserThreadLocal;
import kr.co.popoolserver.provider.JwtProvider;
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
    private ConsumerAuthenticationService consumerAuthenticationService;
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;
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
            UserThreadLocal.set(consumerAuthenticationService.findUserByToken(token));
            logger.debug("User ThreadLocal Create");
        }else if(userRole.equals("ROLE_CORPORATE")){
            CorporateThreadLocal.set(consumerAuthenticationService.findCorporateByToken(token));
            logger.debug("Corporate ThreadLocal Create");
        }else if(userRole.equals("ROLE_ADMIN")){
            AdminThreadLocal.set(adminAuthenticationService.findAdminByToken(token));
            logger.debug("Admin ThreadLocal Create");
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
        if(UserThreadLocal.get() == null && CorporateThreadLocal.get() == null && AdminThreadLocal.get() == null) return;
        if(UserThreadLocal.get() != null){
            UserThreadLocal.remove();
            logger.debug("User ThreadLocal PostHandle Remove");
        }
        if(CorporateThreadLocal.get() != null){
            CorporateThreadLocal.remove();
            logger.debug("Corporate ThreadLocal PostHandle Remove");
        }
        if(AdminThreadLocal.get() != null){
            AdminThreadLocal.remove();
            logger.debug("Admin ThreadLocal PostHandle Remove");
        }
    }
}
