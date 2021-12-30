package br.com.waugustoaf.school.gym.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthInterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> accessibleRoutes = new ArrayList<>();
        accessibleRoutes.add("/sessions");
        accessibleRoutes.add("/sessions/");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(accessibleRoutes);
    }
}
