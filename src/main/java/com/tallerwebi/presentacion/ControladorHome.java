package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class ControladorHome {

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("categorias", Arrays.asList("Almacen", "Perfumeria", "Bebidas", "Verduleria", "Limpieza", "Lacteos"));
        return modelAndView;
    }
}
