package com.funkymonkeys.application.order.service;


import com.funkymonkeys.application.order.dto.BasicProductDTO;
import com.funkymonkeys.application.order.model.Product;
import com.funkymonkeys.application.order.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(BasicProductDTO basicProductDTO) {
        Product product = Product.builder()
                .sku(basicProductDTO.sku())
                .ready(false)
                .orderId(basicProductDTO.orderId())
                .imageUrl(basicProductDTO.imageUrl())
                .name(basicProductDTO.name())
                .imagePath(basicProductDTO.imagePath())
                .build();

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

    public Product updateToggle(Long id) {
        Optional<Product> productUpdate = productRepository.findById(id);
        if(productUpdate.isPresent()) {
            Product product = productUpdate.get();
            product.setReady(!product.isReady());
            productRepository.save(product);
            return product;
        }
        throw new NoSuchElementException(String.format("The id:%s does not exist",id));
    }
}
