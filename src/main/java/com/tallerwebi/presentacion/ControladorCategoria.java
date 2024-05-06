package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCategoria {

    @RequestMapping(path = "/categoria", method = RequestMethod.GET)
    public ModelAndView irAcategoria(@RequestParam("categoria") String categoria) {
        ModelAndView modelAndView = new ModelAndView("categoria");
        modelAndView.addObject("categoria", categoria);
        return modelAndView;
    }

}


