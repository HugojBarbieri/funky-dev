package com.funky.packageservice.resource;

import com.funky.packageservice.dto.OrderDTO;
import com.funky.packageservice.service.PackageService;
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
@RequestMapping("/package")
public class PackageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageXLSResource.class);

    private final PackageService packageService;

    @Autowired
    public PackageResource(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping()
    public ResponseEntity<?> findUnpackagedOrders() {
        LOGGER.info("Calling findUnpackagedOrders endpoint");
        Optional<List<OrderDTO>> orders = packageService.getUnpackagedOrders();
        if(orders.isPresent()) {
            return ResponseEntity.ok(orders.get());
        }

        return ResponseEntity.noContent().build();
    }
}
