package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
                StringBuilder ids = new StringBuilder();
                for (Producto producto : productosBuscados) {
                    if (ids.length() > 0) {
                        ids.append(",");
                    }
                    ids.append(producto.getIdProducto());
                }

                RedirectView redirectView = new RedirectView();
                redirectView.setUrl("/spring/productoFiltrado?productoAbuscar=" + productoABuscar + "&productoIds=" + ids.toString());
                return new ModelAndView(redirectView);
            } else {
                model.put("error", "Productos no encontrados");
            }
        } catch (IllegalArgumentException e) {
            model.put("error", "Producto no encontrado");
        }

        return new ModelAndView("productoBuscado", model);
    }

    @RequestMapping(path = "/productoFiltrado", method = RequestMethod.GET)
    public ModelAndView buscarProductos(
            @RequestParam(value = "categoria", required = false) String categoriaStr,
            @RequestParam(value = "subcategoria", required = false) String subcategoriaStr,
            @RequestParam(value = "tipo", required = false) List<String> tipos,
            @RequestParam(value = "ubicacion", required = false) List<String> ubicaciones,
            @RequestParam(value = "descuento", required = false) List<String> descuentos,
            @RequestParam(value = "marca", required = false) List<String> marcas,
            @RequestParam(value = "supermercado_id", required = false) List<String> supermercados,
            @RequestParam(value = "precio", required = false) List<String> precios,
            @RequestParam(value = "productoAbuscar", required = false) String productoABuscar,
            @RequestParam(value = "productoIds", required = false) String productoIds) {

        ModelMap model = new ModelMap();

        if (productoIds != null && !productoIds.isEmpty()) {
            String[] idsArray = productoIds.split(",");
            List<Integer> ids = new ArrayList<>();
            for (String id : idsArray) {
                ids.add(Integer.parseInt(id));
            }

            List<Producto> productos = servicioBusqueda.consultarProductosPorIds(ids);
            model.put("productoBuscado", productos);
        }

        if (tipos != null || ubicaciones != null || descuentos != null || marcas != null || supermercados != null || precios != null || productoABuscar != null) {
            Map<String, List<String>> filtros = new HashMap<>();
            filtros.put("tipo", tipos);
            filtros.put("ubicacion", ubicaciones);
            filtros.put("descuento", descuentos);
            filtros.put("marca", marcas);
            filtros.put("supermercado_id", supermercados);

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
                            break;
                    }
                }
                filtros.put("precio", filtrosPrecio);
            }

            List<Producto> productosFiltrados = servicioBusqueda.consultarProductosConFiltros(subcategoriaStr, filtros, productoIds);

            if (productosFiltrados != null && !productosFiltrados.isEmpty()) {
                model.put("productos", productosFiltrados);
            } else {
                model.put("error", "Sin resultados");
            }
        } else {
            try {
                Categoria categoria = Categoria.valueOf(categoriaStr);
                Subcategoria subcategoria = Subcategoria.valueOf(subcategoriaStr);

                List<Producto> productosDeLaSubcategoria = servicioBusqueda.consultarProductosPorSubcategoria(subcategoria);
                if (productosDeLaSubcategoria != null && !productosDeLaSubcategoria.isEmpty()) {
                    model.put("productos", productosDeLaSubcategoria);
                    model.put("categoria", categoria.toString());
                    model.put("subcategoria", subcategoria.toString());
                } else {
                    model.put("error", "Productos de esa subcategoría no encontrados");
                }
            } catch (IllegalArgumentException e) {
                model.put("error", "Categoría o subcategoría inválida");
            }
        }

        return new ModelAndView("productoBuscado", model);
    }
}