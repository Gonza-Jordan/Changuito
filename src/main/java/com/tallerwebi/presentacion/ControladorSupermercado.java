package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorSupermercado {

    private ServicioSupermercado servicioSupermercado;

    @Autowired
    public ControladorSupermercado(ServicioSupermercado servicioSupermercado) {
        this.servicioSupermercado = servicioSupermercado;
    }

    @RequestMapping(path = "/supermercados", method = RequestMethod.GET)
    public ModelAndView irASupermercados(@RequestParam(value = "latitud", required = false) Double latitud,
                                         @RequestParam(value = "longitud", required = false) Double longitud) throws IOException, InterruptedException {
        ModelAndView modelAndView = new ModelAndView("supermercados");

        if (latitud != null && longitud != null) {
            modelAndView.addObject("latitud", latitud);
            modelAndView.addObject("longitud", longitud);
        } else {
            latitud = -34.67055556;
            longitud = -58.56277778;
            modelAndView.addObject("latitud", latitud);
            modelAndView.addObject("longitud", longitud);
        }
        modelAndView.addObject("iconoUbicacion", "img/ubicacion.png");
        modelAndView.addObject("iconoSupermercados", "img/supermercados.png");
        List<Supermercado> supermercados = servicioSupermercado.obtenerSupermercados(latitud, longitud, 30);
        modelAndView.addObject("supermercados", supermercados);

        return modelAndView;
    }

}


