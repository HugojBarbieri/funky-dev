package com.funky.packageservice.service;

import com.funky.packageservice.client.FunkyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportProductsService {

    private final FunkyClient funkyClient;

    @Autowired
    public ImportProductsService(FunkyClient funkyClient) {
        this.funkyClient = funkyClient;
    }

    public boolean saveProducts() {
        return true;
    }
}
