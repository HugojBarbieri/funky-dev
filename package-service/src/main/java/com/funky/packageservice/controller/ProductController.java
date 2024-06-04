package com.funky.packageservice.controller;

import com.funky.packageservice.model.Product;
import com.funky.packageservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        LOGGER.info("Calling save product endpoint");
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("productId") Long productId) {
        LOGGER.info(String.format("Calling delete order endpoint with id: %s", productId));
        return ResponseEntity.ok(productService.delete(productId));
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PutMapping("/{productId}/toggle")
    public ResponseEntity<Product> updateProductToggle(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.updateToggle(productId));
    }
}
