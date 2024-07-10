package com.funkymonkeys.application.importProducts.resource;

import com.funkymonkeys.application.importProducts.service.ImportProductsService;
import com.funkymonkeys.application.packageOrder.resource.PackageXLSResource;
import com.funkymonkeys.application.packageOrder.service.PackageService;
import com.funkymonkeys.application.tiendanube.dto.OrderTiendaNubeDTO;
import com.funkymonkeys.application.tiendanube.dto.ProductTiendaNubeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funky/import-products")
public class ImportProductsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportProductsService.class);

    private final ImportProductsService importProductsService;

    @Autowired
    public ImportProductsResource(ImportProductsService importProductsService) {
        this.importProductsService = importProductsService;
    }

    @GetMapping()
    public ResponseEntity<?> findAllProducts() {
        LOGGER.info("Calling findAllProducts endpoint");
        Optional<List<ProductTiendaNubeDTO>> products = importProductsService.getProducts();
        if(products.isPresent()) {
            return ResponseEntity.ok(products.get());
        }

        return ResponseEntity.noContent().build();
    }
}
