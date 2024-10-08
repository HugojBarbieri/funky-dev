package com.funkymonkeys.application.importProducts.service;

import com.funkymonkeys.application.tiendanube.dto.ProductTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.service.TiendaNubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.funkymonkeys.application.order.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

@Service
public class ImportProductsService {

    private final TiendaNubeService tiendaNubeService;

    @Autowired
    public ImportProductsService(TiendaNubeService tiendaNubeService) {
        this.tiendaNubeService = tiendaNubeService;
    }

    public Optional<List<ProductTiendaNubeDTO>> getProducts() {
        return Optional.of(tiendaNubeService.getProducts());
    }
}
