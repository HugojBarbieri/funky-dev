package com.funkymonkeys.application.QR.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class BarcodeGeneratorService {

    private final String outputLocation;

    private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeGeneratorService.class);

    public BarcodeGeneratorService(@Value("${qrcode.output.directory}") String outputLocation) {
        this.outputLocation = outputLocation;
    }

    public String generateBarcodeAndSave(String text) throws Exception {
        // Generate the barcode
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 300, 150);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Ensure the output directory exists
        File directory = new File(outputLocation);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.info("Output directory created at: " + outputLocation);
            } else {
                throw new IOException("Failed to create output directory at: " + outputLocation);
            }
        }

        // Save the image to the specified file path
        File outputFile = new File(directory, text + ".png");
        ImageIO.write(barcodeImage, "png", outputFile);

        return "Barcode saved to " + outputFile.getAbsolutePath();
    }
}
