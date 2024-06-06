package com.funky.packageservice.repository;

import com.funky.packageservice.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductOrder, Long> {
}
