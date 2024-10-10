package com.funkymonkeys.application.QR.resource;

import com.funkymonkeys.application.QR.dto.BarcodeBulkListDTO;
import com.funkymonkeys.application.QR.service.BarcodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funky/barcode")
public class BarcodeResource {

    @Autowired
    private BarcodeGeneratorService barcodeService;

    @GetMapping("/generate/bulk")
    public String generateBulkBarcode(@RequestBody BarcodeBulkListDTO barcodeBulkListDTO) {
        StringBuilder address = new StringBuilder();

        barcodeBulkListDTO.barcodeBulkDTOS.forEach(bar -> {
            try {
                address.append(barcodeService.generateBarcodeAndSave(bar.code, bar.description)).append(",");
            } catch (Exception e) {
                throw new RuntimeException("Error generating barcode", e);
            }
        });
        return address.toString();

    }

    @GetMapping("/generate/{text}/{description}")
    public String generateBarcode(@PathVariable String text, @PathVariable String description) {
        try {
            return barcodeService.generateBarcodeAndSave(text, description);
        } catch (Exception e) {
            throw new RuntimeException("Error generating barcode", e);
        }
    }
}
