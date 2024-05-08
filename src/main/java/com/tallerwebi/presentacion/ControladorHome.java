package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorHome {

    private ControladorHome controladorHome;

    @Autowired
    public ControladorHome(ControladorHome controladorHome) {
        this.controladorHome = controladorHome;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Categoria> categorias = Arrays.asList(Categoria.values());
        modelAndView.addObject("categorias", categorias);
        return modelAndView;
    }
}
