package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ServicioBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorProductoBuscado {
    private ServicioBusqueda servicioBusqueda;

    @Autowired
    public ControladorProductoBuscado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)

    public ModelAndView irAproductoBuscado(@ModelAttribute("producto") Producto producto) {
        ModelMap model = new ModelMap();
        Producto productoBuscado = servicioBusqueda.consultarProducto(producto.getNombre());
        if (productoBuscado != null) {

            model.put("producto", productoBuscado);
            return new ModelAndView("productoBuscado", model);
        }
        return null;
    }

}








