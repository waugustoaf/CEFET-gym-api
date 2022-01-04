package br.com.waugustoaf.school.gym.interceptor;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.UserRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> authorizedExtras = new HashMap<>();
        authorizedExtras.put("/plans", "GET");
        authorizedExtras.put("/plans/", "GET");

        AtomicBoolean isAuthorized = new AtomicBoolean(false);

        authorizedExtras.forEach((key, value) -> {
            if(key.equals(request.getServletPath()) && value.equals(request.getMethod())) {
                isAuthorized.set(true);
            }
        });

        if(isAuthorized.get()) {
            return true;
        }

        String token = request.getHeader("authorization");

        if(token == null) {
            throw new AppException("Token JWT não encontrado. Faça login", 401, "token.notFound");
        }

        String tokenFormatted = token.replaceAll("Bearer ", "");

        String userId = null;

        try {
            userId = jwtUtil.extractId(tokenFormatted);
        } catch (MalformedJwtException mje) {
            throw new AppException("Sessão inválida. Relogue-se", 403, "token.invalid");
        }

        if(userId == null) {
            throw new AppException("Sessão inválida. Relogue-se", 403, "token.invalid");
        }

        Optional<User> user = userRepository.findById(UUID.fromString(userId));

        if(user.isEmpty()) {
            log.info("Empty");
            throw new AppException("Usuário não encontrado. Relogue-se", 403, "token.invalid");
        }

        if(!jwtUtil.validateToken(tokenFormatted, user.get())) {
            throw new AppException("Usuário não encontrado. Relogue-se", 403, "token.invalid");
        }

        return true;
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
