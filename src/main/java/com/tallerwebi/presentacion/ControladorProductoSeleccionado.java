package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProductoSeleccionado {
    private ServicioBusqueda servicioBusqueda;

    @Autowired


    public void ControladorProductoSeleccionado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;

    }

    @RequestMapping(path ="/comparar", method = RequestMethod.GET)

    public ModelAndView irAProductoSeleccionado(@RequestParam ("ids")List<Integer> ids) {

        List<Producto> productos = servicioBusqueda.consultarProductosPorIds(ids);
        ModelMap model = new ModelMap();
        model.addAttribute("productos", productos);

        return new ModelAndView("producto_seleccionado",model);
    }
}
