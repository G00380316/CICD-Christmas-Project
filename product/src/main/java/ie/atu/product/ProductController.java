package ie.atu.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.atu.product.payload.ProductRequest;
import ie.atu.product.payload.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        //TODO: process POST request
        log.info("Controller method Called to Add Product");
        log.info("Product Request: " + productRequest.toString());

        long productID = productService.addProduct(productRequest);
        return new ResponseEntity<>(productID, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductByID(@PathVariable("id") long productID) {
        //TODO: process GET request
        log.info("Controller method Called to get Product by ID");
        log.info("Product ID: " + productID);

        ProductResponse productResponse = productService.getProductbyId(productID);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProductByID(@PathVariable("id") long productID) {
        productService.deleteProductById(productID);
    }

    @PutMapping("reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productID, @RequestParam long quantity) {
        //TODO: process PUT request
        log.info("Controller method Called to reduce quantity");
        log.info("Controller method Called to reduce quantity of this product ID:" + productID + "by" + quantity);
        log.info("Product ID: " + productID);

        productService.reduceQuantity(productID, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
