package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.UserService;
import br.com.waugustoaf.school.gym.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<User> store(@RequestBody @Valid User user) throws URISyntaxException {
        if(user.getId() != null) {
            throw new AppException("Um novo usuário não espera um ID.", "users.noIdOnStore");
        }

        User response = this.userService.save(user);

        return ResponseEntity
                .created(new URI("/api/users/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@RequestBody @Valid User newUser, @PathVariable("id") UUID id) {
        Optional<User> user = this.userService.findOne(id);

        if(!user.isPresent()) {
            throw new AppException("Nenhum usuário com esse ID.", "users.notFound");
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
