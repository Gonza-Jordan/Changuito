package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorProductoBuscado {
    private ServicioBusqueda servicioBusqueda;

    @Autowired
    public ControladorProductoBuscado(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

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


@RequestMapping(path = "/productoBuscado", method = RequestMethod.GET)
public ModelAndView buscarProductos(
        @RequestParam(value = "categoria", required = false) String categoriaStr,
        @RequestParam(value = "subcategoria", required = false) String subcategoriaStr,
        @RequestParam(value = "tipo", required = false) List<String> tipos,
        @RequestParam(value = "ubicacion", required = false) List<String> ubicaciones,
        @RequestParam(value = "descuento", required = false) List<String> descuentos,
        @RequestParam(value = "marca", required = false) List<String> marcas,
        @RequestParam(value = "supermercado", required = false) List<String> supermercados,
        @RequestParam(value = "precio", required = false) List<String> precios) {

    ModelMap model = new ModelMap();

    // Verifica si se han proporcionado filtros
    if (tipos != null || ubicaciones != null || descuentos != null || marcas != null || supermercados != null || precios != null) {
        // Ejecuta la lógica para filtrar productos
        Map<String, List<String>> filtros = new HashMap<>();
        filtros.put("tipo", tipos);
        filtros.put("ubicacion", ubicaciones);
        filtros.put("descuento", descuentos);
        filtros.put("marca", marcas);
        filtros.put("supermercado", supermercados);

        // Agregar filtro de precio
        if (precios != null) {
            List<String> filtrosPrecio = new ArrayList<>();
            for (String precio : precios) {
                switch (precio) {
                    case "menor_a_1000":
                        filtrosPrecio.add("< 1000");
                        break;
                    case "entre_1000_y_3000":
                        filtrosPrecio.add("BETWEEN 1000 AND 3000");
                        break;
                    case "mas_de_3000":
                        filtrosPrecio.add("> 3000");
                        break;
                    default:
                        // No hacer nada o manejar cualquier otro caso si es necesario
                        break;
                }
            }
            filtros.put("precio", filtrosPrecio);
        }

        List<Producto> productosFiltrados = servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros);

        if (productosFiltrados != null && !productosFiltrados.isEmpty()) {
            model.put("productos", productosFiltrados);
        } else {
            model.put("error", "Sin resultados");
        }
    } else {
        // Ejecuta la lógica para buscar productos por categoría y subcategoría
        try {
            Categoria categoria = Categoria.valueOf(categoriaStr);
            Subcategoria subcategoria = Subcategoria.valueOf(subcategoriaStr);

            List<Producto> productosDeLaSubcategoria = servicioBusqueda.consultarProductosPorSubcategoria(subcategoria);
            if (productosDeLaSubcategoria != null && !productosDeLaSubcategoria.isEmpty()) {
                model.put("productos", productosDeLaSubcategoria);
                model.put("categoria", categoria.toString());
                model.put("subcategoria", subcategoria.toString());

            } else {
                model.put("error", "Productos  no encontrados");
            }
        } catch (IllegalArgumentException e) {
            model.put("error", "producto no encontrado");
        }

    } return new ModelAndView("productoBuscado", model);
}