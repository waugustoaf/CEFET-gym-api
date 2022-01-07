package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Plan;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> findAll() {
        return this.planRepository.findAll();
    }

    public Optional<Plan> findOne(UUID id) {
        return this.planRepository.findById(id);
    }

    public Plan save(Plan plan) {
        return this.planRepository.save(plan);
    }

    public void delete(UUID id) {
        boolean planExists = this.planRepository.findById(id).isPresent();

        if(!planExists) {
            throw new AppException("Nenhum plano encontrado com esse ID.", "plans.notFound");
        }

        this.planRepository.deleteById(id);
    }
}
