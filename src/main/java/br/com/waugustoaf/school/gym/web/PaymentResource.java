package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Payment;
import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.PaymentService;
import br.com.waugustoaf.school.gym.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/payments")
public class PaymentResource {
    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentResource(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> index() {
        List<Payment> payments = this.paymentService.findAll();

        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> show(@PathVariable("id") UUID id) {
        Optional<Payment> payment = this.paymentService.findOne(id);

        if(payment.isPresent()) {
            return ResponseEntity.ok().body(payment.get());
        } else {
            throw new AppException("Nenhum pagamento com esse ID foi encontrado.", "payment.notFound");
        }
    }

    @PostMapping
    public ResponseEntity<Payment> update(@RequestBody Map<String, String> json) throws URISyntaxException {
        if(json.get("id") != null) {
            throw new AppException("Um novo pagamento não espera o atribudo ID.", "diets.noIdOnStore");
        }

        Optional<User> user = this.userService.findOne(UUID.fromString(json.get("user_id")));

        if(user.isEmpty()) {
            throw new AppException("Usuário referenciado não encontrado.", "user.notFound");
        }

        Payment payment = new Payment();

        payment.setReceipt_image(json.get("receipt_image"));
        payment.setValue(Float.valueOf(json.get("value")));
        payment.setUser(user.get());

        Payment response = this.paymentService.save(payment);
        return ResponseEntity
                .created(new URI("/payment/" + response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Payment> update(@RequestBody Map<String, String> json, @PathVariable("id") UUID id) {
        Optional<Payment> payment = this.paymentService.findOne(id);

        if(payment.isEmpty()) {
            throw new AppException("Nenhum pagamento com esse ID encontrado.", "diets.notFound");
        }

        Optional<User> user = this.userService.findOne(UUID.fromString(json.get("user_id")));

        if(user.isEmpty()) {
            throw new AppException("Usuário referenciado não encontrado.", "user.notFound");
        }

        Payment paymentExistent = payment.get();

        paymentExistent.setUser(user.get());
        paymentExistent.setValue(Float.valueOf(json.get("value")));
        paymentExistent.setReceipt_image(json.get("receipt_image"));

        return ResponseEntity
                .ok()
                .body(this.paymentService.save(paymentExistent));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.paymentService.delete(id);
    }
}
