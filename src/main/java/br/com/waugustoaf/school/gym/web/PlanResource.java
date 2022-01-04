package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Plan;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/plans")
public class PlanResource {
    private final PlanService planService;

    public PlanResource(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<Plan>> index() {
        List<Plan> plans = this.planService.findAll();
        return ResponseEntity.ok().body(plans);
    }

    @GetMapping("{id")
    public ResponseEntity<Plan> show(@PathVariable("id") UUID id) {
        Optional<Plan> plan = this.planService.findOne(id);

        if(plan.isPresent()) {
            return ResponseEntity.ok().body(plan.get());
        } else {
            throw new AppException("Nenhum plano com esse ID encontrado.", "plans.notFound");
        }
    }

    @PostMapping
    public ResponseEntity<Plan> store(@RequestBody @Valid Plan plan) throws URISyntaxException {
        if(plan.getId() != null) {
            throw new AppException("Um novo plano não espera o atributo ID.", "plans.noIdOnStore");
        }

        Plan response = this.planService.save(plan);

        return ResponseEntity
                .created(new URI("/plans/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Plan> update(@RequestBody @Valid Plan newPlan, @PathVariable("id") UUID id) {
        Optional<Plan> plan = this.planService.findOne(id);

        if(!plan.isPresent()) {
            throw new AppException("Nenhum plano encontrado com esse ID", "plans.notFound");
        }

        newPlan.setId(plan.get().getId());
        newPlan.setCreated_at(plan.get().getCreated_at());

        return ResponseEntity
                .ok()
                .body(this.planService.save(newPlan));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        this.planService.delete(id);

        return ResponseEntity.status(204).body(null);
    }
}
