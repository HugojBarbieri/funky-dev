package com.funky.packageservice.repository;

import com.funky.packageservice.model.ProductName;
import com.funky.packageservice.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductNameRepository extends JpaRepository<ProductName, Long> {
}
