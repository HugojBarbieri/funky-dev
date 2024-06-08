package com.funky.packageservice.resource;

import com.funky.packageservice.dto.ProductDTO;
import com.funky.packageservice.service.ImportProductsService;
import com.funky.packageservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private final ImportProductsService importProductsService;
    private final ProductService productService;

    @Autowired
    public ProductResource(ImportProductsService importProductsService, ProductService productService) {
        this.importProductsService = importProductsService;
        this.productService = productService;
    }

    @GetMapping("/import")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        LOGGER.info("Calling get product endpoint from Tienda Nube");
        return ResponseEntity.ok(importProductsService.getProducts());
    }
    @PostMapping("/import")
    public ResponseEntity<Boolean> saveProductImport() {
        LOGGER.info("Calling save product endpoint from Tienda Nube");
        importProductsService.saveFunkyProducts();
        return ResponseEntity.ok(Boolean.TRUE);
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

}
