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
import java.awt.*;
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

    /**
     * Generate a barcode image with a description underneath it, and save it to the file system.
     * @param text The text for the barcode.
     * @param description The description to display underneath the barcode.
     * @return The absolute path to the saved barcode image file.
     * @throws Exception If an error occurs during barcode generation or saving the image.
     */
    public String generateBarcodeAndSave(String text, String description) throws Exception {
        // Generate the barcode
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 300, 150);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Add description under the barcode
        BufferedImage finalImage = addDescriptionToBarcode(barcodeImage, description);

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
        ImageIO.write(finalImage, "png", outputFile);

        return "Barcode with description saved to " + outputFile.getAbsolutePath();
    }

    /**
     * Combines the barcode image with a text description underneath it.
     * @param barcodeImage The generated barcode image.
     * @param description The description to add below the barcode.
     * @return A new image containing the barcode and the description.
     */
    private BufferedImage addDescriptionToBarcode(BufferedImage barcodeImage, String description) {
        int barcodeWidth = barcodeImage.getWidth();
        int barcodeHeight = barcodeImage.getHeight();

        // Set up font size and margin
        int fontSize = 20;  // Increase the font size for visibility
        int margin = 15;    // Increase margin between barcode and description

        // Create a new image with extra space for the description
        BufferedImage combinedImage = new BufferedImage(barcodeWidth, barcodeHeight + margin + fontSize + 10, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.setColor(Color.WHITE); // Set background to white
        g2d.fillRect(0, 0, combinedImage.getWidth(), combinedImage.getHeight());

        // Draw the barcode on the new image
        g2d.drawImage(barcodeImage, 0, 0, null);

        // Set font and draw description below the barcode
        g2d.setColor(Color.BLACK); // Set font color to black for contrast
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(description);

        // Ensure the text is centered below the barcode
        int x = (barcodeWidth - textWidth) / 2;
        int y = barcodeHeight + margin + fontSize - 5;  // Adjust the baseline for better positioning

        g2d.drawString(description, x, y);  // Draw the description

        // Clean up
        g2d.dispose();

        return combinedImage;
    }

}
