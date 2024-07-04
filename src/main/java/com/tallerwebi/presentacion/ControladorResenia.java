package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Resenia;
import com.tallerwebi.dominio.ServicioResenia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorResenia {
    private ServicioResenia servicioResenia;

    @Autowired
    public ControladorResenia(ServicioResenia servicioResenia) {
        this.servicioResenia = servicioResenia;

    }

    @RequestMapping(path = "/crearResenia",method = RequestMethod.POST)

    public ModelAndView crearResenia(@ModelAttribute("resenia") Resenia resenia, HttpServletRequest request){

        servicioResenia.guardarResenia(resenia);
        return new ModelAndView("producto_seleccionado");


    }

}
