package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Employee;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.EmployeeRepository;
import br.com.waugustoaf.school.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Optional<Employee> findOne(UUID id) {
        return this.employeeRepository.findById(id);
    }

    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public void delete(UUID id) {
        boolean employeeExists = this.employeeRepository.findById(id).isPresent();

        if(!employeeExists) {
            throw new AppException("Nenhum funcionário encontrado com esse ID.", "employees.notFound");
        }

        this.employeeRepository.deleteById(id);
    }
}
