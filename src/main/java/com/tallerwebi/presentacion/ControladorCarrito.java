
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorCarrito {


    private ServicioCarrito servicioCarrito;
    private ServicioUsuario servicioUsuario;
    private ServicioPedido servicioPedido;
    private ServicioSupermercadoProducto servicioSupermercadoProducto;
    private ServicioPromocion servicioPromocion;


    public ControladorCarrito(ServicioCarrito servicioCarrito, ServicioUsuario servicioUsuario, ServicioPedido servicioPedido, ServicioSupermercadoProducto servicioSupermercadoProducto, ServicioPromocion servicioPromocion) {
        // Inicialmente, el carrito está vacío
        this.servicioCarrito = servicioCarrito;
        this.servicioUsuario = servicioUsuario;
        this.servicioPedido = servicioPedido;
        this.servicioSupermercadoProducto = servicioSupermercadoProducto;
        this.servicioPromocion = servicioPromocion;
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {
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


    @RequestMapping(path = "/eliminarDelCarrito", method = RequestMethod.GET)
    public ModelAndView eliminarDelCarrito(
            @RequestParam("idProducto") Integer idProducto,
            @RequestParam("idSupemercado") Integer idSupemercado, HttpServletRequest request) {

        //
        SupermercadoProducto supermercadoProducto;
        supermercadoProducto = this.servicioSupermercadoProducto.consultarSupermercadoProducto(idProducto, idSupemercado);


        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");


        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        //
        Carrito carrito1 = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());


        List<SupermercadoProducto> listSuperProdu = new ArrayList<>();
        for (SupermercadoProducto supermercadoProducto1 : carrito1.getSupermercadoProducto()) {
            if (!supermercadoProducto1.getId().equals(supermercadoProducto.getId())) {
                listSuperProdu.add(supermercadoProducto1);
            }
        }


        carrito1.setSupermercadoProducto(listSuperProdu);
        servicioCarrito.modificar(carrito1);

        return new ModelAndView("redirect:/carritoCompras");

    }


    @RequestMapping(path = "/eliminarDelCarritoPromo", method = RequestMethod.GET)
    public ModelAndView eliminarDelCarrito(@RequestParam("idPromo") Integer idPromo, HttpServletRequest request) {

        //
        Promocion promocion;
        promocion = this.servicioPromocion.buscarPromocion(idPromo);


        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");


        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        //
        Carrito carrito1 = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());


        List<Promocion> listpromocion = new ArrayList<>();
        for (Promocion promocion1 : carrito1.getPromocion()) {
            if (!promocion1.getIdPromocion().equals(promocion.getIdPromocion())) {
                listpromocion.add(promocion1);
            }
        }


        carrito1.setPromocion(listpromocion);
        servicioCarrito.modificar(carrito1);

        return new ModelAndView("redirect:/carritoCompras");

    }


    @RequestMapping(path = "/agregarAlCarrito", method = RequestMethod.GET)
    public ModelAndView agregarAlCarrito(
            @RequestParam("idProducto") Integer idProducto,
            @RequestParam("idSupemercado") Integer idSupemercado, HttpServletRequest request) {

        //
        SupermercadoProducto supermercadoProducto;
        supermercadoProducto = this.servicioSupermercadoProducto.consultarSupermercadoProducto(idProducto, idSupemercado);


        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        if (usuario.getStampCarritoActivo() == null) {
            //
            Carrito carrito1 = new Carrito();

            carrito1.getSupermercadoProducto().add(supermercadoProducto);

            usuario.setStampCarritoActivo(carrito1.getFechaDeCreacion());

//            SupermercadoProducto supermercadoProductoCopy=new SupermercadoProducto(supermercadoProducto);
//            servicioSupermercadoProducto.eliminar(supermercadoProducto);
//            servicioSupermercadoProducto.guardar(supermercadoProductoCopy);

            servicioCarrito.registrar(carrito1);
            servicioUsuario.modificar(usuario);


            misession.setAttribute("usuario", usuario);


        } else {
            //
            Carrito carrito1 = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());

            carrito1.getSupermercadoProducto().add(supermercadoProducto);

//            SupermercadoProducto supermercadoProductoCopy=new SupermercadoProducto(supermercadoProducto);
//            servicioSupermercadoProducto.eliminar(supermercadoProducto);
//            servicioSupermercadoProducto.guardar(supermercadoProductoCopy);

            servicioCarrito.modificar(carrito1);
        }

        return new ModelAndView("redirect:/carritoCompras");
    }


    @RequestMapping(path = "/agregarAlCarritoPromocion", method = RequestMethod.GET)
    public ModelAndView agregarAlCarritoPromocion(@RequestParam("idPromocion") Integer idPromocion, HttpServletRequest request) {
        Promocion promocion = this.servicioPromocion.buscarPromocion(idPromocion);

        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Carrito carrito;
        if (usuario.getStampCarritoActivo() == null) {
            carrito = new Carrito();
            usuario.setStampCarritoActivo(carrito.getFechaDeCreacion());
            servicioCarrito.registrar(carrito);
            servicioUsuario.modificar(usuario);
            misession.setAttribute("usuario", usuario);
        } else {
            carrito = servicioCarrito.consultarCarrito(usuario.getStampCarritoActivo());
        }

        try {
            servicioCarrito.agregarPromocionAlCarrito(carrito.getId(), promocion);
        } catch (RuntimeException e) {
            return new ModelAndView("redirect:/carritoCompras").addObject("error", e.getMessage());
        }

        return new ModelAndView("redirect:/carritoCompras");
    }


    @RequestMapping(path = "/limpiarCarrito", method = RequestMethod.GET)
    public ModelAndView limpiarCarrito(HttpServletRequest request) {

        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        usuario.setStampCarritoActivo(null);

        servicioUsuario.modificar(usuario);

        usuario.setGuardoCarrito(false);

        misession.setAttribute("usuario", usuario);

        return new ModelAndView("redirect:/home");
    }


    //AGREGAR ELIMINAR EN SERVICIO Y REPO CARRITO
    @RequestMapping(path = "/guardarCarrito", method = RequestMethod.GET)
    public ModelAndView guardarCarrito(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Date stamp = usuario.getStampCarritoActivo();
        Carrito carrito = servicioCarrito.consultarCarrito(stamp);

        // Verifica si el carrito ya existe para el usuario
        if (usuario.getCarritos().stream().anyMatch(c -> c.getFechaDeCreacion().equals(carrito.getFechaDeCreacion()))) {
            String mensaje = URLEncoder.encode("El carrito ya está guardado.", StandardCharsets.UTF_8);
            return new ModelAndView("redirect:/carritoCompras?mensaje=" + mensaje);
        }

        // Guardar el carrito
        if (usuario.getGuardoCarrito()) {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setSupermercadoProducto(carrito.getSupermercadoProducto());
            nuevoCarrito.setPromocion(carrito.getPromocion());
            servicioCarrito.registrar(nuevoCarrito);
            usuario.getCarritos().add(nuevoCarrito);
            usuario.setStampCarritoActivo(nuevoCarrito.getFechaDeCreacion());
        } else {
            usuario.getCarritos().add(carrito);
            usuario.setGuardoCarrito(true);
        }

        servicioUsuario.modificar(usuario);
        misession.setAttribute("usuario", usuario);

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

        Usuario usuario1 = servicioUsuario.consultarUsuario(usuario.getEmail());

        misession.setAttribute("usuario", usuario1);

        //servicioUsuario.modificar(usuario);
        //carrito.forEach(producto -> usuario.getProducto().add(producto)); // Formatear el precio de cada producto

        return new ModelAndView("redirect:/home");
    }


    //PEDIDOS
    @RequestMapping(path = "/generarPedido", method = RequestMethod.POST)
    public ModelAndView generarPedido(@RequestParam("tipoPago") TipoDePago tipoPago, HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        //Usuario usuario=servicioUsuario.consultarUsuario(usuario.getEmail());

        Date stamp = usuario.getStampCarritoActivo();
        Carrito carrito = servicioCarrito.consultarCarrito(stamp);

        Double totalPedido = 0.0;
        for (SupermercadoProducto superProdu : carrito.getSupermercadoProducto()) {
            totalPedido += superProdu.getPrecio();
        }

        for (Promocion promo : carrito.getPromocion()) {
            totalPedido += promo.getPrecioFinal();
        }

        Pedido pedido = new Pedido();
        pedido.setCarrito(carrito);
        pedido.setTipoDePago(tipoPago);
        pedido.setTotal(totalPedido);
        servicioPedido.registrar(pedido);

        usuario.getPedidos().add(pedido);
        servicioUsuario.modificarPedidoCarrito(usuario);

        //
        usuario.setStampCarritoActivo(null);
        servicioUsuario.modificar(usuario);

        usuario.setGuardoCarrito(false);


        misession.setAttribute("usuario", usuario);

        return new ModelAndView("redirect:/mi-cuenta");

    }


    @RequestMapping(path = "/irAPagar", method = RequestMethod.GET)
    public ModelAndView irAPagar() {


        return new ModelAndView("tipoDePago");

    }


    @RequestMapping(path = "/pagar", method = RequestMethod.POST)
    public ModelAndView pagar(@RequestParam("tipoPago") TipoDePago tipoPago, HttpServletRequest request) {

        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        Date stamp = usuario.getStampCarritoActivo();
        Carrito carrito = servicioCarrito.consultarCarrito(stamp);

        Double totalPedido = 0.0;
        for (SupermercadoProducto superProdu : carrito.getSupermercadoProducto()) {
            totalPedido += superProdu.getPrecio();
        }

        for (Promocion promo : carrito.getPromocion()) {
            totalPedido += promo.getPrecioFinal();
        }

        ModelAndView modelAndView = new ModelAndView("pago");
        modelAndView.addObject("tipoPago", tipoPago);
        modelAndView.addObject("totalPedido", totalPedido);
        return modelAndView;
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

//    @RequestMapping(path = "/pagar", method = RequestMethod.POST)
//    public String pagar(HttpSession session) {
//        // Vaciar el carrito
//        session.removeAttribute("carrito");
//        return "redirect:/home";
//    }
}
