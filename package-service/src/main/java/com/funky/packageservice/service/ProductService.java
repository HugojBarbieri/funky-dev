package com.funky.packageservice.service;

import com.funky.packageservice.model.Product;
import com.funky.packageservice.model.ProductOrder;
import com.funky.packageservice.repository.ProductOrderRepository;
import com.funky.packageservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public boolean delete(Long productId) {
        Optional<Product> productToDelete= productRepository.findById(productId);
        if(productToDelete.isPresent()) {
            productRepository.delete(productToDelete.get());
            return true;
        }

        return false;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                String.format("The id:%s does not exist",id)));
    }
}
