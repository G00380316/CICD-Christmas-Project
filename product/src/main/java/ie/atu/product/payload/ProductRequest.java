package ie.atu.product.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

    private String name;

    private long price;

    private long quantity;
}
