package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.tallerwebi.dominio.ServicioGeolocalizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ControladorGeolocalizacion {

    @Autowired
    private ServicioGeolocalizacion servicioGeolocalizacion;

    @PostMapping("/search")
    public String buscar(@RequestParam("query") String query, RedirectAttributes redirectAttributes) {
        try {
            JsonNode location = servicioGeolocalizacion.buscarUbicacion(query);
            Double latitud = location.get("lat").asDouble();
            Double longitud = location.get("lon").asDouble();

            redirectAttributes.addAttribute("latitud", latitud);
            redirectAttributes.addAttribute("longitud", longitud);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/supermercados";
    }
}


