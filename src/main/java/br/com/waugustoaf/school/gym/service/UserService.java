package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> findOne(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Boolean validatePassword(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    public List<User> findAllWithDeleted() {
        return this.userRepository.findAllWithDeleted();
    }

    public Optional<User> findUserWithDelete(Long id) {
        return this.userRepository.findUserWithDelete(id);
    }

    public User save(User user) {
        if(user.getId() == null) {
            Optional<User> userAlreadyExists = this.userRepository.findByCpf(user.getCpf());

            if(userAlreadyExists.isPresent()) {
                if(userAlreadyExists.get().getDeleted_at() == null) {
                    throw new AppException("An user with this cpf already exists", "users.alreadyExists");
                } else {
                    throw new AppException(
                            "An user with this cpf is disabled. You can enabled it again", "users.disabled"
                    );
                }
            }

            user.setPassword(encoder.encode(user.getPassword()));
        }

        return this.userRepository.save(user);
    }

    public ResponseEntity<Void> delete(Long id) {
        boolean userExits = this.userRepository.findById(id).isPresent();

        if(!userExits) {
            throw new AppException("Cannot find an user with this id", "users.notFound");
        }

        this.userRepository.deleteById(id);

        return ResponseEntity.status(204).build();
    }
}
