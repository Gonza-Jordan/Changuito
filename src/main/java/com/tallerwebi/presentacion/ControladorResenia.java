package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioResenia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorResenia {
    private ServicioResenia servicioResenia;

    @Autowired
    public ControladorResenia(ServicioResenia servicioResenia) {
        this.servicioResenia = servicioResenia;

    }

    @RequestMapping(path = "/crearResenia",method = RequestMethod.GET)

    public ModelAndView crearResenia(@RequestParam String nombre,
                                     @RequestParam String descripcion,
                                     @RequestParam String categoria,
                                     ModelMap model) {}

    }

}
