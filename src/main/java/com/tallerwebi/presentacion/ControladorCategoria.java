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
    public ModelAndView irACategoria(@RequestParam("categoria") String categoria) {
        ModelAndView modelAndView = new ModelAndView("categoria");
        modelAndView.addObject("categoria", categoria);
        String icono = "img/" + categoria + ".svg";
        modelAndView.addObject("icono", icono);

        Categoria categoriaEnum = Categoria.valueOf(categoria);

        List<Subcategoria> subcategorias = Arrays.asList(categoriaEnum.getSubcategorias());
        modelAndView.addObject("subcategorias", subcategorias);

        // Obtener los productos correspondientes a la categoría y subcategoría seleccionadas
        List<Producto> productos = obtenerProductos(categoriaEnum);
        modelAndView.addObject("productos", productos);

        return modelAndView;
    }

    private List<Producto> obtenerProductos(Categoria categoria) {
        List<Producto> productos = new ArrayList<>();
        switch (categoria) {
            case Almacen:
                // Aquí debes agregar la lógica para obtener los productos de la categoría Almacen
                break;
            case Perfumeria:
                // Aquí debes agregar la lógica para obtener los productos de la categoría Perfumeria
                break;
            // Agrega más casos para las otras categorías...
        }
        return productos;
    }
}
