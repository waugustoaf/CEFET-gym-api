package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.UserService;
import br.com.waugustoaf.school.gym.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> index() {
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable("id") UUID id) {
        Optional<User> user = this.userService.findOne(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            throw new AppException("Nenhum usuário com esse ID encontrado.", "users.notFound");
        }
    }

    @GetMapping("/clients")
    public ResponseEntity<List<User>> getClients() {
        List<User> users = this.userService.findClients();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<User> store(HttpServletRequest request, @RequestBody @Valid User user) throws URISyntaxException {
        if(user.getId() != null) {
            throw new AppException("Um novo usuário não espera um ID.", "users.noIdOnStore");
        }

        if(user.getRole().equals(User.Role.administrator) || user.getRole().equals(User.Role.employee)) {
            String token = request.getHeader("authorization");
            if(token == null) throw new AppException("Token JWT não encontrado. Relogue-se ou crie um usuário não-adm.", 401);
            String formattedToken = token.replaceAll("BEARER ", "");
            String userId = null;
            try {
                userId = this.jwtUtil.extractId(formattedToken);
            } catch (MalformedJwtException mje) {
                throw new AppException("Token JWT inválido. Relogue-se", 401);
            }
            Optional<User> findUser = this.userService.findOne(UUID.fromString(userId));
            if(findUser.isEmpty()) throw new AppException("Usuário logado não encontrado. Relogue-se.", 401);
            if(!findUser.get().getRole().equals(User.Role.administrator)) throw new AppException("Você não tem permissão de cadastrar admin", 403);
        }

        User response = this.userService.save(user);

        return ResponseEntity
                .created(new URI("/users/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(HttpServletRequest request, @RequestBody @Valid User newUser, @PathVariable("id") UUID id) {
        Optional<User> user = this.userService.findOne(id);

        if(!user.isPresent()) {
            throw new AppException("Nenhum usuário com esse ID.", "users.notFound");
        }

        String token = request.getHeader("authorization");
        if(token == null) throw new AppException("Token JWT não encontrado. Relogue-se.", 401);
        String formattedToken = token.replaceAll("BEARER ", "");
        String userId = null;
        try {
            userId = this.jwtUtil.extractId(formattedToken);
        } catch (MalformedJwtException mje) {
            throw new AppException("Token JWT inválido. Relogue-se", 401);
        }
        Optional<User> findUser = this.userService.findOne(UUID.fromString(userId));
        if(findUser.isEmpty()) throw new AppException("Usuário logado não encontrado. Relogue-se.", 401);
        if(!findUser.get().getRole().equals(User.Role.administrator) && findUser.get().getId().equals(user.get().getId()) ) {
            throw new AppException("Você só pode alterar sua conta ou um administrador.", 403);
        }

        newUser.setId(user.get().getId());
        newUser.setCreated_at(user.get().getCreated_at());

        return ResponseEntity
                .ok()
                .body(this.userService.save(newUser));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.userService.delete(id);
    }
}
