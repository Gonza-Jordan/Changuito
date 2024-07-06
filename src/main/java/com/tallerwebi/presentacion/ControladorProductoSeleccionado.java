package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class ControladorProductoSeleccionado {
    private ServicioBusqueda servicioBusqueda;

    @Autowired
    public  ControladorProductoSeleccionado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

    @RequestMapping(path ="/producto_seleccionado", method = RequestMethod.GET)

    public ModelAndView irAProductoSeleccionado(@RequestParam("idSupermercado") Integer idSupermercado,
                                                @RequestParam("id") Integer id,
                                                @RequestParam("subcategoria") Subcategoria subcategoria, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        List<SupermercadoProducto> comparacion = servicioBusqueda.buscarProductosDeLaMismaSubcategoria(subcategoria);
        SupermercadoProducto elegido = servicioBusqueda.buscarProductoIdElegido(id, idSupermercado);


        if (comparacion!= null){
            comparacion.removeIf(producto ->
                    producto.getProducto().getIdProducto().equals(elegido.getProducto().getIdProducto()) &&
                            producto.getSupermercado().getIdSupermercado().equals(elegido.getSupermercado().getIdSupermercado())
            );

            List<SupermercadoProducto> productosOrdenados = servicioBusqueda.ordenarProductos(comparacion, "menor_a_mayor");

            List<SupermercadoProducto> top5Productos = productosOrdenados.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            model.put("productos", top5Productos);

        }else {
            model.put("error", "No hay comparaciones ");
        }

        model.put("elegido", elegido);

        return new ModelAndView("producto_seleccionado", model);
    }
}
