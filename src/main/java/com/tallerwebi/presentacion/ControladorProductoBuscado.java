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

@Controller
public class ControladorProductoBuscado {
//    private ServicioBusqueda servicioBusqueda;
//
//    @Autowired
//    public ControladorProductoBuscado(ServicioBusqueda servicioBusqueda) {
//        this.servicioBusqueda = servicioBusqueda;
//
//    }


    @RequestMapping(path ="/productoBuscado", method = RequestMethod.GET)
    public ModelAndView irAProductoBuscado() { return new ModelAndView("productoBuscado"); }



//    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
//
//    public ModelAndView irAproductoBuscado(@RequestParam("nombre") String nombre) {
//        ModelMap model = new ModelMap();
//        Producto productoBuscado = servicioBusqueda.consultarProducto(nombre);
//        if (productoBuscado != null) {
//            model.put("producto", productoBuscado);
//            return new ModelAndView("productoBuscado", model);
//        }
//        return null;
//    }

}








