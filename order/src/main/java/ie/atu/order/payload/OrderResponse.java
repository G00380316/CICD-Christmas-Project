package ie.atu.order.payload;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long orderID;

    private long amount;

    private String referenceNumber;

    private PaymentType paymentType;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetails {

        private String productName;

        private long productID;

        private long quantity;

        private long price;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentDetails {

        private long productID;

        private PaymentType paymentType;

        private String paymentStatus;

        private Instant paymentDate;
    }
}
