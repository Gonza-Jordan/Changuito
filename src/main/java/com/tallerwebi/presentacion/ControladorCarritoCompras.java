package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
import java.util.List;

@Controller
public class ControladorCarritoCompras {

    private List<SupermercadoProducto> carrito = new ArrayList<>();

    public ControladorCarritoCompras() {
        // Inicialmente, el carrito está vacío
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito() {
        DecimalFormat df = new DecimalFormat("#.00"); // Formato para dos decimales

        for (SupermercadoProducto supermercadoProducto : carrito) {
            Double precio = supermercadoProducto.getPrecio(); // Asegúrate de que getPrecio() devuelve un double
            supermercadoProducto.getProducto().setPrecioFormateado(df.format(precio)); // Formatear el precio de cada producto
        }



//        carrito.forEach(supermercadoProducto -> supermercadoProducto.getProducto().setPrecioFormateado(df.format(supermercadoProducto.getPrecio()))); // Formatear el precio de cada producto
        ModelAndView modelAndView = new ModelAndView("carritoCompras");
        modelAndView.addObject("carrito", carrito);
        modelAndView.addObject("cantidadProductos", carrito.size());
        return modelAndView;
    }

    @RequestMapping(path = "/eliminar-del-carrito", method = RequestMethod.POST)
    public String eliminarDelCarrito(@ModelAttribute("codigoBarras") String codigoBarras) {
        carrito.removeIf(supermercadoProducto -> supermercadoProducto.getProducto().getCodigoBarras().equals(codigoBarras));
        return "redirect:/carritoCompras";
    }

    @RequestMapping(path = "/agregarAlCarrito", method = RequestMethod.POST)
    public String agregarAlCarrito(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") String precio,
            @RequestParam("codigoBarras") String codigoBarras,
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("subcategoria") Subcategoria subcategoria,
            @RequestParam("urlImagen") String urlImagen) {

        Producto producto = new Producto(nombre, codigoBarras, categoria, subcategoria, urlImagen);
        SupermercadoProducto supermercadoProducto = new SupermercadoProducto();
        supermercadoProducto.setProducto(producto);
        supermercadoProducto.setPrecio(Double.parseDouble(precio));
        carrito.add(supermercadoProducto);
        return "redirect:/carritoCompras";
    }

    public void vaciarCarrito() {
        carrito.clear();
    }

    @RequestMapping(path = "/guardarCarrito", method = RequestMethod.GET)
    public ModelAndView guardarCarrito(HttpServletRequest request) {
        HttpSession misession = request.getSession();
        Usuario usuario = (Usuario) misession.getAttribute("usuario");

        carrito.forEach(supermercadoProducto -> usuario.getSupermercadoProducto().add(supermercadoProducto)); // Formatear el precio de cada producto

        return new ModelAndView("redirect:/home");
    }
}
