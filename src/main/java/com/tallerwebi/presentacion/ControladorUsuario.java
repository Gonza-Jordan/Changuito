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
    private ServicioProducto servicioProducto;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioProducto servicioProducto) {
        this.servicioUsuario = servicioUsuario;
        this.servicioProducto = servicioProducto;
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
        Usuario usuario = new Usuario();
        model.put("usuario", usuario);
        return new ModelAndView("nuevoUsuario", model);
    }

    @RequestMapping(path = "/mi-cuenta", method = RequestMethod.GET)
    public ModelAndView irMiCuenta(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario1 = servicioUsuario.consultarUsuario(usuario.getEmail());
        usuario1.getCarritos().removeIf(carrito -> carrito.getSupermercadoProducto().isEmpty() && carrito.getPromocion().isEmpty());

        // Logging para depuración
        usuario1.getCarritos().forEach(carrito -> {
            System.out.println("Carrito fecha: " + carrito.getFechaDeCreacion());
            carrito.getPromocion().forEach(promocion -> {
                if (promocion instanceof Paquete) {
                    System.out.println("Promoción Paquete: " + ((Paquete) promocion).getNombre());
                    ((Paquete) promocion).getProductos().forEach(prod -> {
                        System.out.println("Producto en paquete: " + prod.getProducto().getNombre());
                    });
                } else if (promocion instanceof Combo) {
                    System.out.println("Promoción Combo: " + ((Combo) promocion).getProducto().getProducto().getNombre());
                } else {
                    System.out.println("Tipo de promoción desconocido: " + promocion.getClass().getName());
                }
            });
        });

        ModelMap model = new ModelMap();
        model.put("usuario", usuario1);
        misession.setAttribute("usuario", usuario1); // Actualizar la sesión con el usuario actualizado

        return new ModelAndView("miCuenta", model);
    }

    @RequestMapping(path = "/sign-out", method = RequestMethod.GET)
    public ModelAndView cerrarSession(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        misession.removeAttribute("usuario");
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ModelMap model = new ModelMap();
        Usuario usuario = servicioUsuario.validarContrasena(datosLogin.getEmail(), datosLogin.getContrasena());

        if (usuario != null && !usuario.getAdmin()) {
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            return new ModelAndView("redirect:/home");
        } else if (usuario != null && usuario.getAdmin()) {
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            return new ModelAndView("redirect:/administrador");
        } else {
            redirectAttrs.addFlashAttribute("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttrs) {
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

    @RequestMapping(path = "/modificar", method = RequestMethod.POST)
    public ModelAndView modificar(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        try {
            servicioUsuario.modificar(usuario);
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            return new ModelAndView("redirect:/mi-cuenta");
        } catch (Exception e) {
            ModelMap model = new ModelMap();
            model.put("error", "Error al modificar el usuario: " + e.getMessage());
            return new ModelAndView("miCuenta", model);
        }
    }

    @RequestMapping(path = "/agregarAFavoritos", method = RequestMethod.POST)
    public ModelAndView agregarAFavoritos(@RequestParam("idProductos") List<Integer> idProductos, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && idProductos != null) {
            List<Producto> productos = new ArrayList<>();
            for (Integer idProducto : idProductos) {
                Producto producto = servicioProducto.consultarProductoPorId(idProducto);
                if (producto != null) {
                    productos.add(producto);
                }
            }
            servicioUsuario.agregarAFavoritos(usuario, productos);
            session.setAttribute("usuario", servicioUsuario.consultarUsuario(usuario.getEmail())); // Actualizar la sesión
        }

        return new ModelAndView("redirect:/mi-cuenta");
    }

    @RequestMapping(path = "/eliminarDeFavoritos", method = RequestMethod.POST)
    public ModelAndView eliminarDeFavoritos(@RequestParam Integer idProducto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Producto producto = servicioProducto.consultarProductoPorId(idProducto);

        if (usuario != null && producto != null) {
            servicioUsuario.eliminarDeFavoritos(usuario, producto);
        }

        return new ModelAndView("redirect:/mi-cuenta");
    }

}
