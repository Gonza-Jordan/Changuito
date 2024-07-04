package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service
public class ServicioEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMimeMessageWithImage(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(variables);
        String htmlBody = templateEngine.process(templateName, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        // Adjuntar imágenes de promoción
        FileSystemResource promo1 = new FileSystemResource("src/main/webapp/resources/core/img/promo 1.jpg");
        FileSystemResource promo2 = new FileSystemResource("src/main/webapp/resources/core/img/promo 2.jpg");
        FileSystemResource promo3 = new FileSystemResource("src/main/webapp/resources/core/img/promo 3.jpg");

        helper.addInline("promo1", promo1);
        helper.addInline("promo2", promo2);
        helper.addInline("promo3", promo3);

        mailSender.send(message);
    }
}
