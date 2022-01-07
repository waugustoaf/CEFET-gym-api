package br.com.waugustoaf.school.gym.repository;

import br.com.waugustoaf.school.gym.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCpf(String cpf);
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
}
