package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCategoria {

   // private ServicioCategoria servicioCategoria;


//    @Autowired
//    public ServicioCategoria(ServicioCategoria servicioCategoria) {
//        this.servicioCategoria = servicioCategoria;
//    }

    @RequestMapping(path = "/categoria", method = RequestMethod.GET)
    public ModelAndView irACategoria() {
        return new ModelAndView("categoria");
    }

}