package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCarritoCompras {

    private List<Producto> carrito = new ArrayList<>();

    public ControladorCarritoCompras() {
        // Inicialmente, el carrito está vacío
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito() {
        DecimalFormat df = new DecimalFormat("#.00"); // Formato para dos decimales
        carrito.forEach(producto -> producto.setPrecioFormateado(df.format(producto.getPrecio()))); // Formatear el precio de cada producto
        ModelAndView modelAndView = new ModelAndView("carritoCompras");
        modelAndView.addObject("carrito", carrito);
        modelAndView.addObject("cantidadProductos", carrito.size());
        return modelAndView;
    }

    @RequestMapping(path = "/eliminar-del-carrito", method = RequestMethod.POST)
    public String eliminarDelCarrito(@ModelAttribute("codigoBarras") String codigoBarras) {
        carrito.removeIf(producto -> producto.getCodigoBarras().equals(codigoBarras));
        return "redirect:/carritoCompras";
    }

    @RequestMapping(path = "/agregarAlCarrito", method = RequestMethod.POST)
    public String agregarAlCarrito(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("codigoBarras") String codigoBarras,
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("subcategoria") Subcategoria subcategoria) {

        Producto producto = new Producto(nombre, precio, codigoBarras, categoria, subcategoria, "");
        carrito.add(producto);
        return "redirect:/carritoCompras";
    }
}
