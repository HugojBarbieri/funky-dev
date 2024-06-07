package com.funky.packageservice.resource;

import com.funky.packageservice.model.ProductOrder;
import com.funky.packageservice.service.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products-order")
public class ProductOrderResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderResource(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping()
    public ResponseEntity<ProductOrder> saveProduct(@RequestBody ProductOrder productOrder) {
        LOGGER.info("Calling save productOrder endpoint");
        return ResponseEntity.ok(productOrderService.save(productOrder));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("productId") Long productId) {
        LOGGER.info(String.format("Calling delete order endpoint with id: %s", productId));
        return ResponseEntity.ok(productOrderService.delete(productId));
    }

    @GetMapping()
    public ResponseEntity<List<ProductOrder>> findAll() {
        return ResponseEntity.ok(productOrderService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductOrder> findById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productOrderService.findById(productId));
    }

    @PutMapping("/{productId}/toggle")
    public ResponseEntity<ProductOrder> updateProductToggle(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productOrderService.updateToggle(productId));
    }


    //TODO call productService and get url to create a product ready
}
