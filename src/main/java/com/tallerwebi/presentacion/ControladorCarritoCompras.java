
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorCarritoCompras {

    //private List<Producto> carrito = new ArrayList<>();
    private ServicioCarrito servicioCarrito;
    private ServicioUsuario servicioUsuario;
    private ServicioPedido servicioPedido;
    private ServicioSupermercadoProducto servicioSupermercadoProducto;


    public ControladorCarritoCompras(ServicioCarrito servicioCarrito, ServicioUsuario servicioUsuario, ServicioPedido servicioPedido,ServicioSupermercadoProducto servicioSupermercadoProducto) {
        // Inicialmente, el carrito está vacío
        this.servicioCarrito = servicioCarrito;
        this.servicioUsuario = servicioUsuario;
        this.servicioPedido = servicioPedido;
        this.servicioSupermercadoProducto = servicioSupermercadoProducto;
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario==null){
            return new ModelAndView("redirect:/login");
        }

        Carrito carrito;

        if (usuario.getStampCarritoActivo() == null) {
            carrito = new Carrito();
        } else {
            carrito = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());
        }

        //DecimalFormat df = new DecimalFormat("#.00"); // Formato para dos decimales
        //carrito.forEach(producto -> producto.setPrecioFormateado(df.format(producto.getPrecio()))); // Formatear el precio de cada producto
        ModelAndView modelAndView = new ModelAndView("carritoCompras");
        modelAndView.addObject("carrito", carrito);
        modelAndView.addObject("cantidadProductos", carrito.getSupermercadoProducto().size());
        return modelAndView;
    }

    @RequestMapping(path = "/agregarAlCarrito", method = RequestMethod.GET)
    public String agregarAlCarrito(
            @RequestParam("idProducto") Integer idProducto,
            @RequestParam("idSupemercado") Integer idSupemercado, HttpServletRequest request) throws UsuarioExistente {

        //
        SupermercadoProducto supermercadoProducto;
        supermercadoProducto=this.servicioSupermercadoProducto.consultarSupermercadoProducto(idProducto, idSupemercado);


        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");


        if (usuario.getStampCarritoActivo() == null) {
            //
            Carrito carrito1 = new Carrito();

            carrito1.getSupermercadoProducto().add(supermercadoProducto);
            //usuario.getCarritos().add(carrito1);

            usuario.setStampCarritoActivo(carrito1.getFechaDeCreacion());

            misession.setAttribute("usuario", usuario);

            servicioCarrito.registrar(carrito1);

        } else {
            //
            Carrito carrito1 = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());

            carrito1.getSupermercadoProducto().add(supermercadoProducto);
            //usuario.getCarritos().add(carrito1);

            //misession.setAttribute("usuario", usuario);

            servicioCarrito.modificar(carrito1);

            //servicioCarrito.registrar(carrito1);
            //servicioUsuario.modificar(usuario);
        }


        return "redirect:/carritoCompras";

//        ModelAndView modelAndView = new ModelAndView("carritoComprasPrueba2");
//        modelAndView.addObject("supermercadoProducto", supermercadoProducto);
//        return modelAndView;
    }

    @RequestMapping(path = "/limpiarCarrito", method = RequestMethod.GET)
    public ModelAndView limpiarCarrito(HttpServletRequest request) {

        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        usuario.setStampCarritoActivo(null);
        misession.setAttribute("usuario", usuario);

        servicioUsuario.modificar(usuario);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/guardarCarrito", method = RequestMethod.GET)
    public ModelAndView guardarCarrito(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");


        Date stamp = usuario.getStampCarritoActivo();

        Carrito carrito = servicioCarrito.consultarCarrito(stamp);

        usuario.getCarritos().add(carrito);

        servicioUsuario.modificar(usuario);

        misession.setAttribute("usuario", usuario);


        //carrito.forEach(producto -> usuario.getProducto().add(producto)); // Formatear el precio de cada producto

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/reutilizarCarrito", method = RequestMethod.GET)
    public ModelAndView reutilizarCarrito(
            @RequestParam("id") Long id, HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        usuario.setStampCarritoActivo(servicioCarrito.consultarCarritoPorId(id).getFechaDeCreacion());

        servicioUsuario.modificar(usuario);

        misession.setAttribute("usuario", usuario);

//        //carrito.forEach(producto -> usuario.getProducto().add(producto)); // Formatear el precio de cada producto

        return new ModelAndView("redirect:/carritoCompras");
    }

    @RequestMapping(path = "/eliminarCarrito", method = RequestMethod.GET)
    public ModelAndView eliminarCarrito(
            @RequestParam("id") Long id, HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        Carrito carrito = servicioCarrito.consultarCarritoPorId(id);

        servicioUsuario.eliminarCarritoDeUsuario(usuario, carrito);

        Usuario usuario1=servicioUsuario.consultarUsuario(usuario.getEmail());

        misession.setAttribute("usuario", usuario1);

        //servicioUsuario.modificar(usuario);
        //carrito.forEach(producto -> usuario.getProducto().add(producto)); // Formatear el precio de cada producto

        return new ModelAndView("redirect:/home");
    }

  
    //PEDIDOS
    @RequestMapping(path = "/generarPedido", method = RequestMethod.POST)
    public ModelAndView generarPedido(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        Date stamp = usuario.getStampCarritoActivo();

        Carrito carrito = servicioCarrito.consultarCarrito(stamp);

        Pedido pedido = new Pedido();
        pedido.setCarrito(carrito);
        servicioPedido.registrar(pedido);

        usuario.getPedidos().add(pedido);
        servicioUsuario.modificar(usuario);

        misession.setAttribute("usuario", usuario);

        return new ModelAndView("redirect:/mi-cuenta");
    }


    //
//    public void vaciarCarrito() {
//        carrito.clear();
//    }
//
//    @RequestMapping(path = "/vaciarCarrito", method = RequestMethod.POST)
//    public ModelAndView vaciarCarrito(HttpSession session) {
//        carrito.clear();
//        session.setAttribute("carrito", carrito);
//        return new ModelAndView("redirect:/home");
//    }

    @RequestMapping(path = "/pagar", method = RequestMethod.POST)
    public String pagar(HttpSession session) {
        // Vaciar el carrito
        session.removeAttribute("carrito");
        return "redirect:/home";
    }
}
