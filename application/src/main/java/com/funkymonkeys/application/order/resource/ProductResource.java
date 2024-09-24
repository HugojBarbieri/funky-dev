package com.funkymonkeys.application.order.resource;

import com.funkymonkeys.application.order.dto.BasicProductDTO;
import com.funkymonkeys.application.order.model.Product;
import com.funkymonkeys.application.order.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funky/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Product> saveProduct(@RequestBody BasicProductDTO basicProductDTO) {
        LOGGER.info("Calling save productOrder endpoint");
        return ResponseEntity.ok(productService.save(basicProductDTO));
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

    @PutMapping("/{productId}/check")
    public ResponseEntity<Product> checkProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.checkProduct(productId));
    }

    @PutMapping("/{productId}/uncheck")
    public ResponseEntity<Product> uncheckProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.uncheckProduct(productId));
    }
}
