package com.funky.packageservice.service;

import com.funky.packageservice.model.ProductOrder;
import com.funky.packageservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductOrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductOrder> findAll() {
        return productRepository.findAll();
    }

    public ProductOrder save(ProductOrder productOrder) {
        return productRepository.save(productOrder);
    }

    public boolean delete(Long productId) {
        Optional<ProductOrder> productToDelete= productRepository.findById(productId);
        if(productToDelete.isPresent()) {
            productRepository.delete(productToDelete.get());
            return true;
        }

        return false;
    }

    public ProductOrder findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                String.format("The id:%s does not exist",id)));
    }

    public ProductOrder updateToggle(Long id) {
        Optional<ProductOrder> productUpdate = productRepository.findById(id);
        if(productUpdate.isPresent()) {
            ProductOrder productOrder = productUpdate.get();
            productOrder.setReady(!productOrder.isReady());
            productRepository.save(productOrder);
            return productOrder;
        }
        throw new NoSuchElementException(String.format("The id:%s does not exist",id));
    }
}
