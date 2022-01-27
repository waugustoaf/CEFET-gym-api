package br.com.waugustoaf.school.gym.repository;

import br.com.waugustoaf.school.gym.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {}
