package com.funky.packageservice.resource;

import com.funky.packageservice.dto.ProductDTO;
import com.funky.packageservice.model.Order;
import com.funky.packageservice.service.ImportProductsService;
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

    @Autowired
    public ProductResource(ImportProductsService importProductsService) {
        this.importProductsService = importProductsService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts() {
        LOGGER.info("Calling save productOrder endpoint");
        return ResponseEntity.ok(importProductsService.getProducts());
    }

//    @PostMapping()
//    public ResponseEntity<Order> saveProduct(@RequestBody ProductDTO productDTO) {
//        LOGGER.info("Calling save product endpoint explicitly, only for testing");
//        return ResponseEntity.ok(.save(order));
//    }
//
//    @PostMapping("/import")
//    public ResponseEntity<Order> saveProductImport() {
//        LOGGER.info("Calling save order endpoint");
//        return ResponseEntity.ok(orderService.save(order));
//    }
}
