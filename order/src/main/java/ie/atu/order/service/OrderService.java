package ie.atu.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ie.atu.order.payload.order.OrderRequest;
import ie.atu.order.payload.order.OrderResponse;
import ie.atu.order.payload.payment.PaymentRequest;
import ie.atu.order.payload.payment.PaymentResponse;
import ie.atu.order.payload.product.ProductResponse;
import ie.atu.order.db.OrderRepo;
import ie.atu.order.exception.*;
import ie.atu.order.model.Order;

import java.time.Instant;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

        private final OrderRepo orderRepo;
        private final RestTemplate restTemplate;

        public long placeOrder(OrderRequest orderRequest) {

        log.info("Placing Order method is called");
        log.info("Placing Order Request: " + orderRequest.toString());

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productID(orderRequest.getProductID())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        log.info("Creating Order with Status CREATED");

        order = orderRepo.save(order);

        log.info("Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderID(order.getId())
                .paymentType(orderRequest.getPaymentType())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
        log.info("Payment done Successfully. Changing the Order status to PLACED" + paymentRequest);
        orderStatus = "PLACED";
        } catch (Exception e) {
        log.error("Changing order status to PAYMENT_FAILED");
        orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepo.save(order);

        log.info("Order Placed successfully with Order Id: {}", order.getId());

        return order.getId();
}

        public OrderResponse getOrderDetails(long orderId) {
        log.info("Getting order details for Order Id : {}", orderId);

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderServiceException("Order not found for the order Id:" + orderId,
                        "NOT_FOUND", 404));

        log.info("Talking to the Product service to fetch the product for id: {}", order.getProductID());

        ProductResponse productResponse = restTemplate.getForObject(
                "http://localhost:8081/product/" + order.getProductID(),
                ProductResponse.class
        );
        

        log.info("Getting payment information from the payment Service");

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://localhost:8082/payment/order/" + order.getId(),
                PaymentResponse.class
        );

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productID(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails
                .builder()
                .paymentID(paymentResponse.getPaymentID())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentType(paymentResponse.getPaymentType())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderID(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        log.info("orderResponse : " + orderResponse.toString());

        return orderResponse;
        }
}