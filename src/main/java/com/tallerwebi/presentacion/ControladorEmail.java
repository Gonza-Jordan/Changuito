package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorEmail {

    @Autowired
    private ServicioEmail servicioEmail;

    @GetMapping("/send-email")
    public String sendEmail() {
        servicioEmail.sendSimpleMessage("destinatario@example.com", "Asunto", "Contenido del mensaje");
        return "emailEnviado";
    }
}
