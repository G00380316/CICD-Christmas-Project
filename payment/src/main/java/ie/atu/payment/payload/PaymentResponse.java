package ie.atu.payment.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    
    private long orderID;

    private long amount;

    private String referenceNumber;

    private PaymentType paymentType;

    private String status;

    private Instant paymentDate;

    private long paymentID;

}
