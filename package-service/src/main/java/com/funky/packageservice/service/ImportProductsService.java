package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportProductsService {

    private final FunkyClient funkyClient;

    @Autowired
    public ImportProductsService(FunkyClient funkyClient) {
        this.funkyClient = funkyClient;
    }

    public List<ProductDTO> getProducts() {
        return funkyClient.getProducts();
    }

    //TODO do the save
    //TODO look the order if it has the correct photo, then use that one, not the one in the product
}
