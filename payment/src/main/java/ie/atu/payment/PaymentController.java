package ie.atu.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.atu.payment.payload.PaymentRequest;
import ie.atu.payment.payload.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<Long> takePayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Controller method Called to take Payment");
        log.info("Payment Request: " + paymentRequest.toString());

        long paymentID = paymentService.proccessPayment(paymentRequest);
        return new ResponseEntity<>(paymentID, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<PaymentResponse> getOrderByID(@PathVariable("orderID") long orderID) {
        log.info("Controller method Called to get Order by ID");
        log.info("Order ID: " + orderID);

        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderID(orderID);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
