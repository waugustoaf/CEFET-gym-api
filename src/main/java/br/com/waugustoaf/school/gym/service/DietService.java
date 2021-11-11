package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Diet;
import br.com.waugustoaf.school.gym.repository.DietRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
