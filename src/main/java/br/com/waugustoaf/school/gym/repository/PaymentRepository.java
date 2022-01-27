package br.com.waugustoaf.school.gym.repository;

import br.com.waugustoaf.school.gym.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {}
