package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Exercise;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
public class ExerciseResource {
    private final ExerciseService exerciseService;

    public ExerciseResource(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> index() {
        List<Exercise> exercises = this.exerciseService.findAll();

        return ResponseEntity.ok().body(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> show(@PathVariable("id") UUID id) {
        Optional<Exercise> exercise = this.exerciseService.findOne(id);

        if(exercise.isPresent()) {
            return ResponseEntity.ok().body(exercise.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Exercise> store(@RequestBody Exercise exercise) throws URISyntaxException {
        if(exercise.getId() != null) {
            throw new AppException("Um novo exercício não espera o atributo ID", "exercises.noIdOnStore");
        }

        Exercise response = this.exerciseService.save(exercise);
        return ResponseEntity
                .created(new URI("/api/exercises/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Exercise> update(@RequestBody @Valid Exercise newExercise, @PathVariable("id") UUID id) {
        Optional<Exercise> exercise = this.exerciseService.findOne(id);

        if(exercise.isEmpty()) {
            throw new AppException("Nenhum exercício com esse ID encontrada.", "exercises.notFound");
        }

        newExercise.setId(exercise.get().getId());
        newExercise.setCreated_at(exercise.get().getCreated_at());

        return ResponseEntity
                .ok()
                .body(this.exerciseService.save(newExercise));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.exerciseService.delete(id);
    }
}
