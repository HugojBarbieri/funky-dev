package com.funkymonkeys.application.order.repository;


import com.funkymonkeys.application.order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderNumber(int number);
    Optional<Product> findByTiendaNubeId(Long tiendaNubeID);
    @Query("SELECT p.tiendaNubeId FROM Product p WHERE p.orderNumber = :number AND p.ready = :ready")
    List<Long> findTiendaNubeIdByOrderNumberAndReady(@Param("number") int number, @Param("ready") boolean ready);

}
