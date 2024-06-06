package com.funky.packageservice.controller;

import com.funky.packageservice.service.QRCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRResource {

    private final QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    public QRResource(QRCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    @PostMapping("/qrcode")
    public String generateQR(@RequestBody String message) {
        qrCodeGeneratorService.generateQRCode(message);
        return String.format("created QR Code %s", message);
    }
}
