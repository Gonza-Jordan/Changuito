package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProductoBuscado {
    private ServicioBusqueda servicioBusqueda;

    @Autowired
    public ControladorProductoBuscado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

//    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
//    public ModelAndView irAProductoBuscado(@RequestParam("categoria") String categoriaStr, @RequestParam("subcategoria") String subcategoriaStr) {
//        ModelMap model = new ModelMap();
//        try {
//            Categoria categoria = Categoria.valueOf(categoriaStr);
//            Subcategoria subcategoria = Subcategoria.valueOf(subcategoriaStr);
//
//            List<Producto> productosDeLaSubcategoria = servicioBusqueda.consultarProductosPorSubcategoria(subcategoria);
//            if (productosDeLaSubcategoria != null && !productosDeLaSubcategoria.isEmpty()) {
//                model.put("productos", productosDeLaSubcategoria);
//                String titulo = "Productos de la categoría " + categoria.toString() + " y subcategoría " + subcategoria.toString();
//                model.put("titulo", titulo);
//            } else {
//                model.put("error", "Productos de esa subcategoría no encontrados");
//            }
//        } catch (IllegalArgumentException e) {
//            model.put("error", "Categoría o subcategoría inválida");
//        }
//
//        return new ModelAndView("productoBuscado", model);
//    }
//
//    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
//    public ModelAndView irAProductoBuscado(@RequestParam("categoria") String categoriaStr, @RequestParam("subcategoria") String subcategoriaStr) {
//        ModelMap model = new ModelMap();
//        try {
//            Categoria categoria = Categoria.valueOf(categoriaStr);
//            Subcategoria subcategoria = Subcategoria.valueOf(subcategoriaStr);
//
//            List<Producto> productosDeLaSubcategoria = servicioBusqueda.consultarProductosPorSubcategoria(subcategoria);
//            if (productosDeLaSubcategoria != null && !productosDeLaSubcategoria.isEmpty()) {
//                model.put("productos", productosDeLaSubcategoria);
//                String titulo = "Productos de la categoría " + categoria.toString() + " y subcategoría " + subcategoria.toString();
//                model.put("titulo", titulo);
//            } else {
//                model.put("error", "Productos de esa subcategoría no encontrados");
//            }
//        } catch (IllegalArgumentException e) {
//            model.put("error", "Categoría o subcategoría inválida");
//        }
//
//        return new ModelAndView("productoBuscado", model);
//    }
    @RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
    public ModelAndView irAProductoBuscado(@RequestParam("productoAbuscar") String productoAb) {
        ModelMap model = new ModelMap();
        try {
            String productoABuscar = productoAb;
            List<Producto> productosBuscados = servicioBusqueda.consultarProductoPorNombre(productoABuscar);
            if (productosBuscados != null && !productosBuscados.isEmpty()) {
                model.put("productos", productosBuscados);
                String titulo = "Productos de la categoría " + productosBuscados.get(0).getCategoria().toString() + " y subcategoría " + productosBuscados.get(0).getSubcategoria().toString();
                model.put("titulo", titulo);
            } else {
                model.put("error", "Productos  no encontrados");
            }
        } catch (IllegalArgumentException e) {
            model.put("error", "producto no encontrado");
        }

        return new ModelAndView("productoBuscado", model);
    }
}
