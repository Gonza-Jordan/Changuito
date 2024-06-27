package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }


    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {

        ModelMap model = new ModelMap();

        Usuario usuario= new Usuario();
        model.put("usuario", usuario);
        return new ModelAndView("nuevo-usuario", model);
    }


    @RequestMapping(path = "/mi-cuenta", method = RequestMethod.GET)
    public ModelAndView irMiCuenta(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {

            return new ModelAndView("redirect:/login");
        }

        Usuario usuario1=servicioUsuario.consultarUsuario(usuario.getEmail());
        usuario1.getCarritos().removeIf(carrito -> carrito.getSupermercadoProducto().isEmpty());

        ModelMap model = new ModelMap();
        model.put("usuario", usuario1);

        return new ModelAndView("mi-cuenta", model);
    }


    @RequestMapping(path = "/sign-out", method = RequestMethod.GET)
    public ModelAndView cerrarSession(HttpServletRequest request) {
        HttpSession misession = request.getSession();

        Usuario usuario = (Usuario) misession.getAttribute("usuario");
        usuario.setStampCarritoActivo(null);
        servicioUsuario.modificar(usuario);

        misession.removeAttribute("usuario");

        return new ModelAndView("redirect:/home");
    }


    // FUNCIONES POST Y PUT
    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ModelMap model = new ModelMap();

        Usuario usuario = servicioUsuario.validarContrasena(datosLogin.getEmail(), datosLogin.getContrasena());

        if (usuario != null) {
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);


            return new ModelAndView("redirect:/home");
        } else {
            redirectAttrs.addFlashAttribute("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("redirect:/login");

    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttrs) {
        System.out.println("Usuario received: " + usuario);

//        ModelMap model = new ModelMap();
//        model.put("usuario", usuario);
//
//        return new ModelAndView("prueba", model);
        try {
            servicioUsuario.registrar(usuario);
        } catch (UsuarioExistente e) {
            redirectAttrs.addFlashAttribute("error", "El usuario ya existe");
            return new ModelAndView("redirect:/nuevo-usuario");

        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("redirect:/nuevo-usuario");
        }
        redirectAttrs.addFlashAttribute("register", "Usuario registrado correctamente");
        return new ModelAndView("redirect:/login");
    }

    //AL MODIFICAR USARIO NO ME TRAE LOS CARRITOS Y LOS PEDIDOS (ModelAttribute("usuario") Usuario usuario YA EL USUARIO DEL MODEL NO ME LO TRAE)
    @RequestMapping(path = "/modificar", method = RequestMethod.POST)
    public ModelAndView modificar(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {


        servicioUsuario.modificar(usuario);

        HttpSession misession = request.getSession(true);
        misession.setAttribute("usuario", usuario);


        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/pruebaUser", method = RequestMethod.GET)
    public ModelAndView pruebaUser(@RequestParam("email") String email, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        model.put("usuario", servicioUsuario.consultarUsuario(email));

        return new ModelAndView("prueba", model);
    }

    @RequestMapping(path = "/pruebaUser2", method = RequestMethod.GET)
    public ModelAndView pruebaUser2(HttpServletRequest request) {

        HttpSession misession = request.getSession(true);
        Usuario usuario = (Usuario) misession.getAttribute("usuario");


        ModelMap model = new ModelMap();
        model.put("usuario", usuario.getStampCarritoActivo().toString());

        return new ModelAndView("prueba", model);
    }

    @RequestMapping(path = "/pruebaAdmin", method = RequestMethod.GET)
    public ModelAndView admin(HttpServletRequest request) {

        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (!usuario.getAdmin()) {
            return new ModelAndView("redirect:/home");
        }

        return new ModelAndView("redirect:/mi-cuenta");

    }


}
