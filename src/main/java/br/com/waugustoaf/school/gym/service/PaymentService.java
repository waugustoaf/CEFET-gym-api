package br.com.waugustoaf.school.gym.service;

import br.com.waugustoaf.school.gym.domain.Payment;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return this.paymentRepository.findAll();
    }

    public Optional<Payment> findOne(UUID id) {
        return this.paymentRepository.findById(id);
    }

    public Payment save(Payment payment) {
        return this.paymentRepository.save(payment);
    }

    public void delete(UUID id) {
        Optional<Payment> paymentExists = this.paymentRepository.findById(id);

        if(paymentExists.isEmpty()) {
            throw new AppException("Nenhum pagamento encontrado com esse ID.", "payment.notFound");
        }

        this.paymentRepository.deleteById(id);
    }
}
