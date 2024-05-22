package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
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
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

//    @RequestMapping(path = "/home", method = RequestMethod.GET)
//    public ModelAndView irAHome(HttpServletRequest request) {
//
//        HttpSession misession = request.getSession();
//        Usuario usuario = (Usuario) misession.getAttribute("usuario");
//
//        ModelMap model = new ModelMap();
//        model.put("usuario", usuario);
//
//        return new ModelAndView("home", model);
//    }

    @RequestMapping(path = "/mi-cuenta", method = RequestMethod.GET)
    public ModelAndView irMiCuenta(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        ModelMap model = new ModelMap();
        model.put("usuario", usuario);

        return new ModelAndView("mi-cuenta", model);
    }


    @RequestMapping(path = "/sign-out", method = RequestMethod.GET)
    public ModelAndView cerrarSession(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        misession.removeAttribute("usuario");

        return new ModelAndView("redirect:/home");
    }

//    @RequestMapping(path = "/", method = RequestMethod.GET)
//    public ModelAndView inicio() {
//        return new ModelAndView("redirect:/login");
//    }


    // FUNCIONES POST Y PUT
    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ModelMap model = new ModelMap();

        Usuario usuario = servicioLogin.validarContrasena(datosLogin.getEmail(), datosLogin.getContrasena());

        if (usuario != null) {
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);

//            model.put("usuario", usuario);
//            return new ModelAndView("mi-cuenta", model);

            return new ModelAndView("redirect:/home");
        } else {
//            model.put("error", "Usuario o clave incorrecta");
            redirectAttrs.addFlashAttribute("error", "Usuario o clave incorrecta");
        }

//        return new ModelAndView("login", model);
        return new ModelAndView("redirect:/login");

    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttrs) {
        RedirectAttributes redirAttr;
        ModelMap model = new ModelMap();

        try {
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e) {
            redirectAttrs.addFlashAttribute("error", "El usuario ya existe");
            return new ModelAndView("redirect:/nuevo-usuario");

//            model.put("error", "El usuario ya existe");
//            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("redirect:/nuevo-usuario");

//            model.put("error", "Error al registrar el nuevo usuario");
//            return new ModelAndView("nuevo-usuario", model);
        }
        redirectAttrs.addFlashAttribute("register", "Usuario registrado correctamente");
        return new ModelAndView("redirect:/login");

//        // Si uso redirect no me toma DatosLogin()
//        ModelAndView modelAndView = new ModelAndView("login");
//
//        modelAndView.addObject("register", "Usuario registrado correctamente");
//        modelAndView.addObject("datosLogin", new DatosLogin());
//
////        model.put("register", "Usuario registrado correctamente");
////        return new ModelAndView("redirect:/login",model);
//
//        return modelAndView;


    }


}
