package br.com.waugustoaf.school.gym.interceptor;

import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    private Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        String tokenFormatted = token.replaceAll("Bearer ", "");

        if(tokenFormatted == null) {
            throw new AppException("Token JWT não encontrado. Faça login", 401, "token.notFound");
        }

        String userId = null;

        try {
            userId = jwtUtil.extractId(tokenFormatted);
        } catch (MalformedJwtException mje) {
            throw new AppException("Sessão inválida. Relogue-se", 403, "token.invalid");
        }

        log.info(userId);

        if(userId == null) {
            throw new AppException("Sessão inválida. Relogue-se", 403, "token.invalid");
        }

        return userId != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
