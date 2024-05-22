package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

@Controller
public class ControladorCarritoCompras {

    private List<Producto> carrito = new ArrayList<>();

    public ControladorCarritoCompras() {
        carrito.add(new Producto("Manzanas", 2.0, "123456789012", Categoria.Verduleria, Subcategoria.Manzana));
        carrito.add(new Producto("Leche", 3.0, "987654321098", Categoria.Lacteos, Subcategoria.Leche));
        carrito.add(new Producto("Pan", 4.0, "192837465091", Categoria.Almacen, Subcategoria.Harina));
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito() {
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
}