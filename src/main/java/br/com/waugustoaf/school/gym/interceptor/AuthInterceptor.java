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
import java.util.*;
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token, Authorization");

        if(request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Max-Age", "1728000");
            response.setStatus(204);
        }

        List<String[]> guestAuth = new ArrayList<>();
        guestAuth.add(new String[]{"/plans", "GET"});
        guestAuth.add(new String[]{"/users", "POST"});
        guestAuth.add(new String[]{"/users", "PUT"});
        guestAuth.add(new String[]{"/sessions", "POST"});

        List<String[]> clientAuth = new ArrayList<>();
        clientAuth.add(new String[]{"/users", "PUT"});
        clientAuth.add(new String[]{"/diets", "GET"});

        List<String[]> employeeAuth = new ArrayList<>();
        employeeAuth.add(new String[]{"/diets", "POST"});
        employeeAuth.add(new String[]{"/diets", "PUT"});
        employeeAuth.add(new String[]{"/users/clients", "GET"});

        for(String[] route : guestAuth) {
            if(request.getServletPath().startsWith(route[0]) && route[1].equals(request.getMethod())) {
                return true;
            }
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
            throw new AppException("Usuário não encontrado. Relogue-se", 403, "token.invalid");
        }

        if(!jwtUtil.validateToken(tokenFormatted, user.get())) {
            throw new AppException("Usuário não encontrado. Relogue-se", 403, "token.invalid");
        }

        User.Role userRole = user.get().getRole();

        if(userRole == User.Role.administrator) {
            return true;
        }

        for(String[] route : clientAuth) {
            if(request.getServletPath().startsWith(route[0]) && route[1].equals(request.getMethod())) {
                return true;
            }
        }

        for(String[] route : employeeAuth) {
            if(
                    request.getServletPath().startsWith(route[0])
                    && route[1].equals(request.getMethod())
                    && (userRole == User.Role.employee || userRole == User.Role.administrator)
            ) {
                return true;
            }
        }

        throw new AppException("Você não tem permissão", 403, "error.unauthorized");
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
