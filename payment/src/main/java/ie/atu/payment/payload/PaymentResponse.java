package ie.atu.payment.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    
    private long paymentID;

    private String status;

    private PaymentType paymentType;

    private long amount;

}
