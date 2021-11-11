package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sessions")
public class SessionResource {
    private final Logger log = LoggerFactory.getLogger(SessionResource.class);
    private final UserService userService;

    public SessionResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> body) {
        Optional<User> user = this.userService.findByEmail(body.get("email"));

        if(user.isEmpty()) {
            throw new AppException("Email or password invalid");
        }

        if(userService.validatePassword(user.get(), body.get("password"))) {
            Map<String, Object> response = new HashMap<>();

            response.put("user", user.get());
            response.put("token", "");

            return ResponseEntity.ok().body(response);
        } else {
            throw new AppException("Email or password invalid");
        }
    }
}
