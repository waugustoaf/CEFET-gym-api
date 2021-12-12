package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Diet;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.DietRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietService {
    private final Logger log = LoggerFactory.getLogger(DietService.class);
    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public List<Diet> findAll() {
        return this.dietRepository.findAll();
    }

    public Optional<Diet> findOne(Long id) {
        return this.dietRepository.findById(id);
    }

    public Diet save(Diet diet) {
        return this.dietRepository.save(diet);
    }

    public ResponseEntity<Void> delete(Long id) {
        Optional<Diet> dietExists = this.dietRepository.findById(id);

        if(dietExists.isEmpty()) {
            throw new AppException("Cannot find a diet with this id", "diets.notFound");
        }

        this.dietRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
