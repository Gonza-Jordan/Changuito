package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorProductoBuscado {
    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        ModelAndView modelAndView = new ModelAndView("productoBuscado");
        return modelAndView;
    }
}
