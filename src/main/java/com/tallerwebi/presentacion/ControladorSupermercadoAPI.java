package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class ControladorSupermercadoAPI {

    private ServicioSupermercadoAPI servicioSupermercadoAPI;

    @Autowired
    public ControladorSupermercadoAPI(ServicioSupermercadoAPI servicioSupermercadoAPI) {
        this.servicioSupermercadoAPI = servicioSupermercadoAPI;
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
        List<SupermercadoAPI> supermercados = servicioSupermercadoAPI.obtenerSupermercados(latitud, longitud, 30);
        modelAndView.addObject("supermercados", supermercados);

        return modelAndView;
    }

}


