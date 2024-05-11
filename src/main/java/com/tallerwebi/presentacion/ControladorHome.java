package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class ControladorHome {

    public ControladorHome() {
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Categoria> categorias = Arrays.asList(Categoria.values());
        modelAndView.addObject("categorias", categorias);
        Map<String, String> iconos = new HashMap<>();
        int i;
        for (i= 0; i < categorias.size(); i++){
            iconos.put(String.valueOf(categorias.get(i)), "img/" + categorias.get(i) + ".svg");
        }
        modelAndView.addObject("iconos", iconos);
        modelAndView.addObject("producto", new Producto());
        return modelAndView;


    }
//    @RequestMapping("/home")
//    public ModelAndView irAbuscar() {
//
//        ModelMap modelo = new ModelMap();
//        modelo.put("producto", new Producto());
//        return new ModelAndView("home", modelo);
//    }
}
