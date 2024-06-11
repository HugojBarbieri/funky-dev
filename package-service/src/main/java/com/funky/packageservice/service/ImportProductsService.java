package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import com.funky.packageservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportProductsService {

    private final FunkyUtils funkyUtils;

    @Autowired
    public ImportProductsService(FunkyUtils funkyUtils) {
        this.funkyUtils = funkyUtils;
    }

    public List<ProductDTO> getProducts() {
        return funkyUtils.getAllProducts();
    }
}
