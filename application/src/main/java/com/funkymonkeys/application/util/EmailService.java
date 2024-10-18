package com.funkymonkeys.application.util;

import com.funkymonkeys.application.order.model.Order;
import com.funkymonkeys.application.order.model.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmailService {


    private final JavaMailSender emailSender;
    private final String emailSenderTo;

    @Autowired
    public EmailService(JavaMailSender emailSender, @Value("${config-email.email-sender-to}") String emailSenderTo) {
        this.emailSender = emailSender;
        this.emailSenderTo = emailSenderTo;
    }

    public void sendEmail(Order order) {
        try {
            // Create the MimeMessage
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the email details
            helper.setTo(emailSenderTo);
            helper.setSubject(String.format("Orden #%d fue empaquetada", order.getNumber()));
            String productNames = order.getProducts().stream()
                    .map(Product::getName)  // Assuming Product has a getName() method
                    .collect(Collectors.joining(", "));

            helper.setText(String.format("La orden fue empaquetada con éxito y los productos fueron: %s", productNames));

            // Send the email
            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }
}