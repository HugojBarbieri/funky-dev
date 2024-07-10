package com.funkymonkeys.application.QR.resource;

import com.funkymonkeys.application.QR.service.QRCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funky/qr")
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
