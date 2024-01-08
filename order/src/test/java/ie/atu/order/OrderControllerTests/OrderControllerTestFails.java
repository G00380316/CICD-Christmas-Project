package ie.atu.product.ProductControllerTests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ie.atu.order.controller.OrderController;
import ie.atu.order.service.OrderService;



@WebMvcTest(OrderController.class)
public class OrderControllerTestFails {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private OrderService orderService;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
        // Perform common setup steps, if any
        }

        @Test
        public void addProduct_shouldReturnBadRequest() throws Exception {
        ProductRequest productRequest = new ProductRequest(null, 100L, 50L);
        when(productService.addProduct(any(ProductRequest.class)))
                .thenThrow(new ProductServiceException("Error Code:", "PRODUCT_NOT_ADDED"));

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
        }

        @Test
        public void addProduct_shouldReturnisNotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest("Product", 100L, 50L);
        when(productService.addProduct(any(ProductRequest.class)))
                .thenThrow(new ProductServiceException("Error Code:","PRODUCT_NOT_ADDED"));

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNotFound());
        }
}
