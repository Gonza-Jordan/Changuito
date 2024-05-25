package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorCategoria {

    public ControladorCategoria() {
    }

    @RequestMapping(path = "/categoria", method = RequestMethod.GET)
    public ModelAndView irACategoria(@RequestParam("categoria") Categoria categoria) {
        ModelAndView modelAndView = new ModelAndView("categoria");
        modelAndView.addObject("categoria", categoria);
        String icono = "img/" + categoria.toString() + ".svg";
        modelAndView.addObject("icono", icono);

        List<Subcategoria> subcategorias = Arrays.asList(categoria.getSubcategorias());
        modelAndView.addObject("subcategorias", subcategorias);

        // Obtener los productos correspondientes a la categoría y subcategoría seleccionadas
        List<Producto> productos = obtenerProductos(categoria);
        modelAndView.addObject("productos", productos);

        return modelAndView;
    }

    private List<Producto> obtenerProductos(Categoria categoria) {
        // Aquí deberías implementar la lógica para obtener los productos según la categoría
        return new ArrayList<>();
    }
}
