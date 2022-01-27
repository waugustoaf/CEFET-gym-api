package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Exercise;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.ExerciseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll() {
        return this.exerciseRepository.findAll();
    }

    public Optional<Exercise> findOne(UUID id) {
        return this.exerciseRepository.findById(id);
    }

    public Exercise save(Exercise exercise) {
        return this.exerciseRepository.save(exercise);
    }

    public ResponseEntity<Void> delete(UUID id) {
        Optional<Exercise> exerciseExists = this.exerciseRepository.findById(id);

        if(exerciseExists.isEmpty()) {
            throw new AppException("Nenhum exercício com esse ID encontrado.", "exercise.notFound");
        }

        this.exerciseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
