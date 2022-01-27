package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Diet;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/diets")
public class DietResource {
    private final Logger log = LoggerFactory.getLogger(DietResource.class);
    private final DietService dietService;

    public DietResource(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping
    public ResponseEntity<List<Diet>> index() {
        List<Diet> diets = this.dietService.findAll();

        return ResponseEntity.ok().body(diets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diet> show(@PathVariable("id") UUID id) {
        Optional<Diet> diet = this.dietService.findOne(id);

        if(diet.isPresent()) {
            return ResponseEntity.ok().body(diet.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Diet> store(@RequestBody @Valid Diet diet) throws URISyntaxException {
        if(diet.getId() != null) {
            throw new AppException("Uma nova dieta não espera o atributo ID", "diets.noIdOnStore");
        }

        Diet response = this.dietService.save(diet);
        return ResponseEntity
                .created(new URI("/api/diets/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Diet> update(@RequestBody @Valid Diet newDiet, @PathVariable("id") UUID id) {
        Optional<Diet> diet = this.dietService.findOne(id);

        if(diet.isEmpty()) {
            throw new AppException("nenhum dieta com esse ID encontrada.", "diets.notFound");
        }

        newDiet.setId(diet.get().getId());
        newDiet.setCreated_at(diet.get().getCreated_at());

        return ResponseEntity
                .ok()
                .body(this.dietService.save(newDiet));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.dietService.delete(id);
    }
}
