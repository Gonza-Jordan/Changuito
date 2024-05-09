package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorCategoria {

    public ControladorCategoria() {
    }

    @RequestMapping(path = "/categoria", method = RequestMethod.GET)
    public ModelAndView irACategoria(@RequestParam("categoria") String categoria) {
        ModelAndView modelAndView = new ModelAndView("categoria");
        modelAndView.addObject("categoria", categoria);
        String icono = "img/" + categoria + ".svg";
        modelAndView.addObject("icono", icono);

        Categoria categoriaEnum = Categoria.valueOf(categoria);

        List<Subcategoria> subcategorias = Arrays.asList(categoriaEnum.getSubcategorias());
        modelAndView.addObject("producto", new Producto());
        modelAndView.addObject("subcategorias", subcategorias);

        return modelAndView;

    }

}


