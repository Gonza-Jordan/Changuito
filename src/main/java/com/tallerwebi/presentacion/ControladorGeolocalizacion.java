package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.tallerwebi.dominio.ServicioGeolocalizacion;
import com.tallerwebi.dominio.SupermercadoAPI;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/searchDirUsuario")
    public ModelAndView buscarDirUsuario(@RequestParam("query") String query, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        try{
            JsonNode location = servicioGeolocalizacion.buscarUbicacionUsuario(query);
            //JsonNode dirs = location.get("display_name");

            List<Object> dirs = new ArrayList<>();
            for (JsonNode loc : location) {
                dirs.add(loc.get("display_name"));
            }

            List<String> dirsStr = new ArrayList<>();
            for (Object d : dirs) {
                dirsStr.add(d.toString().replaceAll("\"", ""));
            }


            redirectAttrs.addFlashAttribute("directions", dirsStr);

        } catch (IOException e) {
            return new ModelAndView("redirect:/mi-cuenta");

        } catch (InterruptedException e) {
            return new ModelAndView("redirect:/mi-cuenta");

        }
//            Double latitud = location.get("lat").asDouble();
//            Double longitud = location.get("lon").asDouble();

//        HttpSession misession = request.getSession();
//        Usuario usuario = (Usuario) misession.getAttribute("usuario");
//
//
//        redirectAttrs.addFlashAttribute("usuario", usuario);


        return new ModelAndView("redirect:/nuevo-usuario");
    }
}


