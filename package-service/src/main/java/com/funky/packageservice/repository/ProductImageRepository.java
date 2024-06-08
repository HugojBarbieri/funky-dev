package com.funky.packageservice.repository;

import com.funky.packageservice.model.ProductImage;
import com.funky.packageservice.model.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
