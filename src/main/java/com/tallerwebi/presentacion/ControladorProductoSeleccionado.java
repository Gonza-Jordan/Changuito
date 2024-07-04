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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorProductoSeleccionado {
    private ServicioBusqueda servicioBusqueda;


    @Autowired


    public  ControladorProductoSeleccionado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;

    }

    @RequestMapping(path ="/producto_seleccionado", method = RequestMethod.GET)

    public ModelAndView irAProductoSeleccionado(@RequestParam("idSupermercado") Integer idSupermercado,
            @RequestParam ("id") Integer id, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        List<SupermercadoProducto> comparacion = servicioBusqueda.buscarProductoACompararId(id);
        SupermercadoProducto elegido = servicioBusqueda.buscarProductoIdElegido(id, idSupermercado);
        if (comparacion!= null){
            model.put("productos", comparacion);
            model.put("elegido", elegido);
        }else {
            model.put("error", "No hay comaparaciones ");
        }

        return new ModelAndView("producto_seleccionado", model);
    }
}
