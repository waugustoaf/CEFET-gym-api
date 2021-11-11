package br.com.waugustoaf.school.gym.repository;

import br.com.waugustoaf.school.gym.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE users.cpf = ?1", nativeQuery = true)
    Optional<User> findByCpf(String cpf);

    @Query(value = "SELECT * FROM users WHERE users.id = ?1", nativeQuery = true)
    Optional<User> findUserWithDelete(Long id);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAllWithDeleted();
}
