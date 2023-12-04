package miwm.job4me.repositories.payment;

import miwm.job4me.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findBySessionId(String sessionId);

}
