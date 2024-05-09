package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ServicioBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(path = "/productoBuscado", method = RequestMethod.POST)

    public ModelAndView irAproductoBuscado(@ModelAttribute("producto")Producto producto,HttpServletRequest request ) {
        ModelMap model = new ModelMap();

        ModelAndView modelAndView = new ModelAndView("productoBuscado");

        return modelAndView;
    }
}

@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {


    Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
    if (usuarioBuscado != null) {
        request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
        return new ModelAndView("redirect:/home");
    } else {
        model.put("error", "Usuario o clave incorrecta");
    }
    return new ModelAndView("login", model);
