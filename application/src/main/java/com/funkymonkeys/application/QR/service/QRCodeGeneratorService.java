package com.funkymonkeys.application.QR.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class QRCodeGeneratorService {

    private final String outputLocation;
    private static final String charset = "UTF-8";
    private static final String strDateFormat = "yyyyMMddhhmmss";

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeGeneratorService.class);


    public QRCodeGeneratorService(@Value("qrcode.output.directory") String outputLocation) {
        this.outputLocation = outputLocation;
    }

    public void generateQRCode(String message) {
        try {
            processQRCode(message, prepareOutputFileName(), charset, 400, 400);
        } catch (WriterException | IOException e) {
            LOGGER.error(String.format("Error generating the QR for message: %s", message), e);
        }
    }

    private String prepareOutputFileName() {
        //TODO create a file name related to sku and seq
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

        StringBuilder sb = new StringBuilder();
        sb.append(outputLocation).append("/").append("QRCode-").append(dateFormat.format(date)).append(".png");

        return sb.toString();
    }

    private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToPath(matrix, path.substring(path.lastIndexOf(".") + 1), new File(path).toPath());
    }
}
