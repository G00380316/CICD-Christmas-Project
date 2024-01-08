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
public class OrderControllerTestSuccess {

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
    public void addProduct_shouldReturnCreated() throws Exception {
        ProductRequest productRequest = new ProductRequest("Sample Product", 100L, 50L);
        when(productService.addProduct(any(ProductRequest.class))).thenReturn(1L);

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getProductById_shouldReturnOk() throws Exception {
        long productId = 1L;
        ProductResponse productResponse = new ProductResponse("Sample Product", productId, 100L, 50L);
        when(productService.getProductbyId(anyLong())).thenReturn(productResponse);

        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(100L))
                .andExpect(jsonPath("$.quantity").value(50L));
    }
}
