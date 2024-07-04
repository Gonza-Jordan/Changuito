package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ControladorEmail {

    @Autowired
    private ServicioEmail servicioEmail;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to) {
        try {
            Map<String, Object> variables = new HashMap<>();
            servicioEmail.sendMimeMessageWithImage(to, "Tu changuito", "emailTemplate", variables);
            return "Email enviado exitosamente!";
        } catch (MessagingException | IOException e) {
            return "Error enviando email: " + e.getMessage();
        }
    }
}
