package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorEmail {

    @Autowired
    private ServicioEmail servicioEmail;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        try {
            servicioEmail.sendSimpleMessage(to, subject, body);
            return "Email enviado exitosamente!";
        } catch (Exception e) {
            return "Error enviando email: " + e.getMessage();
        }
    }
}
