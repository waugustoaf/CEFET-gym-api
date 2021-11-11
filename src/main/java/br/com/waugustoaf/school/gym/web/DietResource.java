package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Diet;
import br.com.waugustoaf.school.gym.service.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class DietResource {
    private final Logger log = LoggerFactory.getLogger(DietResource.class);
    private final DietService dietService;

    public DietResource(DietService dietService) {
        this.dietService = dietService;
    }

    public ResponseEntity<List<Diet>> index() {
        List<Diet> users = this.dietService.findAll();

        return ResponseEntity.ok().body(users);
    }
}
