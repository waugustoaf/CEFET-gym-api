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
import java.util.UUID;

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

    public Optional<User> findOne(UUID id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> findByCPF(String CPF) {
        return this.userRepository.findByCpf(CPF);
    }

    public List<User> findClients() {
        return this.userRepository.findByRole(User.Role.client);
    }

    public Boolean validatePassword(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    public User save(User user) {
        if(user.getId() == null) {
            Optional<User> userAlreadyExists = this.userRepository.findByCpf(user.getCpf());

            if(userAlreadyExists.isPresent()) {
                throw new AppException("Um usuário com esse CPF já existe", "users.alreadyExists");
            }

            user.setPassword(encoder.encode(user.getPassword()));
        }

        return this.userRepository.save(user);
    }

    public ResponseEntity<Void> delete(UUID id) {
        boolean userExits = this.userRepository.findById(id).isPresent();

        if(!userExits) {
            throw new AppException("Nenhum usuário com esse ID encontrado.", "users.notFound");
        }

        this.userRepository.deleteById(id);

        return ResponseEntity.status(204).build();
    }
}
