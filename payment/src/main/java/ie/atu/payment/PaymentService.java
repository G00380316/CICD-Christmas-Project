package ie.atu.payment;

import java.time.Instant;

import org.springframework.stereotype.Service;

import ie.atu.payment.payload.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepo paymentRepo;

    public long proccessPayment(PaymentRequest paymentRequest) {
        
        log.info("Procces Payment method has been called");
        log.info("Recording Payment Details: {}", paymentRequest);

        Payment payment = Payment.builder().paymentDate(Instant.now())
        .paymentType(paymentRequest.getPaymentType().name())
        .paymentStatus("SUCCESS")
        .orderID(paymentRequest.getOrderID())
        .referenceNumber(paymentRequest.getReferenceNumber())
        .amount(paymentRequest.getAmount())
                .build();

        payment = paymentRepo.save(payment);

        log.info("Transaction has been Completed with Id: {}", payment.getId());

        return payment.getId();
    }
}
