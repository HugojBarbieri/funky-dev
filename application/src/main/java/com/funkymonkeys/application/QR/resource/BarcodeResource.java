package com.funkymonkeys.application.QR.resource;

import com.funkymonkeys.application.QR.service.BarcodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funky/barcode")
public class BarcodeResource {

    @Autowired
    private BarcodeGeneratorService barcodeService;

    @GetMapping("/generate/{text}")
    public String generateBarcode(@PathVariable String text) {
        try {
            return barcodeService.generateBarcodeAndSave(text);
        } catch (Exception e) {
            throw new RuntimeException("Error generating barcode", e);
        }
    }
}
