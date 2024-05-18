package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCarritoCompras {

    private List<Producto> carrito = new ArrayList<>();

    public ControladorCarritoCompras() {
        // Ejemplo de productos pre-cargados en el carrito
        carrito.add(new Producto("Manzanas", 2.00, "123456789012", null, null));
        carrito.add(new Producto("Leche", 3.00, "987654321098", null, null));
        carrito.add(new Producto("Pan", 4.00, "192837465091", null, null));
    }

    @RequestMapping(path = "/carritoCompras", method = RequestMethod.GET)
    public ModelAndView verCarrito() {
        ModelAndView modelAndView = new ModelAndView("carritoCompras");
        modelAndView.addObject("carrito", carrito);
        return modelAndView;
    }

    @RequestMapping(path = "/eliminar-del-carrito", method = RequestMethod.POST)
    public String eliminarDelCarrito(@ModelAttribute("codigoBarras") String codigoBarras) {
        carrito.removeIf(producto -> producto.getCodigoBarras().equals(codigoBarras));
        return "redirect:/carrito";
    }
}
