package com.funky.funkyjobs.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/sendEmail")
    @Async
    public ResponseEntity<String> sendEmail() {
        try {
            // Load the Excel file from the root directory
            String filePath = "orders.xlsx";
            Path path = Paths.get(filePath);
            byte[] fileContent = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            // Create the MimeMessage
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the email details
            helper.setTo(System.getenv("EMAIL_SENDER"));
            helper.setSubject("Ordenes para imprimir");
            helper.setText("Esta en el adjunto");

            // Attach the Excel file
            helper.addAttachment("orders.xlsx", resource);

            // Send the email
            emailSender.send(message);

            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}
