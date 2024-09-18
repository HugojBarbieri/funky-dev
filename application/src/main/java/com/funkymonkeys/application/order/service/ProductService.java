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
        Optional<Product> productDB = productRepository.findByTiendaNubeId(basicProductDTO.id());
        if (productDB.isEmpty()) {
            Product product = Product.builder()
                    .tiendaNubeId(basicProductDTO.id())
                    .sku(basicProductDTO.sku())
                    .ready(false)
                    .orderId(basicProductDTO.orderId())
                    .orderNumber(basicProductDTO.orderNumber())
                    .imageUrl(basicProductDTO.imageUrl())
                    .name(basicProductDTO.name())
                    .imagePath(basicProductDTO.imagePath())
                    .check(basicProductDTO.check())
                    .barcode(basicProductDTO.barcode())
                    .quantity(basicProductDTO.quantity())
                    .build();

            return productRepository.save(product);
        }

        return productDB.get();
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

    public Product checkProduct(Long productTiendaNubeId) {
        Optional<Product> productUpdate = productRepository.findByTiendaNubeId(productTiendaNubeId);
        if(productUpdate.isPresent()) {
            Product product = productUpdate.get();

            if (!product.isReady()) {
                product.setCheck(product.getCheck() + 1);
                if (product.getQuantity() == product.getCheck()) {
                    product.setReady(true);
                }
                productRepository.save(product);
            }
            return product;

        }
        throw new NoSuchElementException(String.format("The id:%s does not exist", productTiendaNubeId));
    }

    public Product uncheckProduct(Long productTiendaNubeId) {
        Optional<Product> productUpdate = productRepository.findByTiendaNubeId(productTiendaNubeId);
        if(productUpdate.isPresent()) {
            Product product = productUpdate.get();
            if(product.getCheck() > 0) {
                product.setCheck(product.getCheck() - 1);
                product.setReady(false);
                productRepository.save(product);
            }

            return product;
        }
        throw new NoSuchElementException(String.format("The id:%s does not exist", productTiendaNubeId));

    }

    public List<Product> getProducts(int orderNumber) {
        return productRepository.findByOrderNumber(orderNumber);
    }

    public List<Long> getReadyProducts(int orderNumber) {
        return productRepository.findTiendaNubeIdByOrderNumberAndReady(orderNumber, true);
    }
}
