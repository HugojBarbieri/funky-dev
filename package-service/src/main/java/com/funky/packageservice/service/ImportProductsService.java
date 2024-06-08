package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.ProductDTO;
import com.funky.packageservice.model.Product;
import com.funky.packageservice.model.ProductImage;
import com.funky.packageservice.repository.ProductImageRepository;
import com.funky.packageservice.repository.ProductNameRepository;
import com.funky.packageservice.repository.ProductRepository;
import com.funky.packageservice.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportProductsService {

    private final FunkyClient funkyClient;
    private final ProductRepository productRepository;

    @Autowired
    public ImportProductsService(FunkyClient funkyClient, ProductRepository productRepository) {
        this.funkyClient = funkyClient;
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProducts() {
        return funkyClient.getProducts();
    }

    public void saveFunkyProducts() {
        List<Product> products = funkyClient.getProducts().stream().map(Product::new).toList();
        products.forEach(p -> {
            Product product = productRepository.findByTiendaNubeId(p.getTiendaNubeId()).orElse(p);
            productRepository.save(product);
        });
    }

    private void mergeProduct(Product existingProduct, Product newProduct) {
        existingProduct.setPublished(newProduct.isPublished());
        existingProduct.setCanonicalUrl(newProduct.getCanonicalUrl());
        existingProduct.setTags(newProduct.getTags());
        existingProduct.setName(newProduct.getName());

        mergeProductImages(existingProduct, newProduct.getImages());
    }

    private void mergeProductImages(Product existingProduct, List<ProductImage> newImages) {
        List<ProductImage> existingImages = existingProduct.getImages();

        for (ProductImage newImage : newImages) {
            boolean exists = existingImages.stream()
                    .anyMatch(existingImage -> existingImage.getSrc().equals(newImage.getSrc()));

            if (!exists) {
                newImage.setProduct(existingProduct);
                existingImages.add(newImage);
            }
        }

        // Remove images that are no longer present in the newImages
        existingImages.removeIf(existingImage ->
                newImages.stream().noneMatch(newImage -> newImage.getSrc().equals(existingImage.getSrc()))
        );

        existingProduct.setImages(existingImages);
    }
}
