package ie.atu.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByOrderID(long orderID);
    
}
