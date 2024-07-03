package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service("servicioBusqueda")
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {

    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;

    @Autowired
    public ServicioBusquedaImpl(RepositorioProducto repositorioProducto, RepositorioSupermercadoProducto repositorioSupermercadoProducto) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioSupermercadoProducto = repositorioSupermercadoProducto;
    }

    @Override
    public List<Producto> consultarProductoPorNombre(String nombre) {
        return repositorioProducto.buscarProductoPorNombre(nombre);
    }

    @Override
    public List<Producto> consultarProductosPorSubcategoria(Subcategoria subcategoria) {
        return repositorioProducto.buscarProductosPorSubcategoria(subcategoria);
    }

    @Override
    public List<Producto> consultarProductoPorCategoria(Categoria categoria) {
        return repositorioProducto.buscarProductoPorCategoria(categoria);
    }

    @Override
    public List<SupermercadoProducto> consultarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds) {
        List<Integer> ids = null;
        if (productoIds != null && !productoIds.isEmpty()) {
            ids = new ArrayList<>();
            for (String id : productoIds.split(",")) {
                ids.add(Integer.parseInt(id));
            }
        }
        return repositorioSupermercadoProducto.buscarConFiltros(subcategoriaStr, filtros, ids);
    }

    @Override
    public List<Producto> consultarProductosPorIds(List<Integer> ids) {
        return repositorioProducto.buscarProductosPorIds(ids);
    }

    @Override
    public List<SupermercadoProducto> ordenarProductos(List<SupermercadoProducto> productosFiltrados, String ordenar) {
        if ("menor_a_mayor".equals(ordenar)) {
            productosFiltrados.sort(Comparator.comparingDouble(this::getPrecioConDescuento));
        } else if ("mayor_a_menor".equals(ordenar)) {
            productosFiltrados.sort(Comparator.comparingDouble(this::getPrecioConDescuento).reversed());
        }
        return productosFiltrados;
    }

    @Override
    public List<Supermercado> consultarSupermercados(List<SupermercadoProducto> productosFiltrados) {
        List<Supermercado> supermercados = new ArrayList<>();
        for (SupermercadoProducto supermercadoProducto : productosFiltrados) {
            Supermercado supermercado = supermercadoProducto.getSupermercado();
            if (!supermercados.contains(supermercado)) {
                supermercados.add(supermercado);
            }
        }
        return supermercados;
    }

    @Override
    public List<Double> consultarDescuentos(List<SupermercadoProducto> productosFiltrados) {
        List<Double> descuentos = new ArrayList<>();
        for (SupermercadoProducto supermercadoProducto : productosFiltrados) {
            Double descuento = supermercadoProducto.getDescuento();
            if (!descuentos.contains(descuento) && descuento != null) {
                descuentos.add(descuento);
            }
        }
        return descuentos;
    }

    @Override
    public List<String> consultarPrecios(List<SupermercadoProducto> productosFiltrados) {
        List<String> precios = new ArrayList<>();

        if (!productosFiltrados.isEmpty()) {
            Double menorPrecio = Double.MAX_VALUE;
            Double mayorPrecio = Double.MIN_VALUE;

            for (SupermercadoProducto supermercadoProducto : productosFiltrados) {
                Double precio;
                if (supermercadoProducto.getDescuento() != null){
                    precio = supermercadoProducto.getPrecio() * supermercadoProducto.getDescuento();
                }else {
                    precio = supermercadoProducto.getPrecio();
                }
                if (precio < menorPrecio) {
                    menorPrecio = precio;
                }
                if (precio > mayorPrecio) {
                    mayorPrecio = precio;
                }
            }
            Double tercioPrecio = menorPrecio + ((mayorPrecio - menorPrecio) / 3);
            Double dosTerciosPrecio = menorPrecio + (2 * (mayorPrecio - menorPrecio) / 3);

            precios.add(String.valueOf(menorPrecio));
            precios.add(String.valueOf(tercioPrecio));
            precios.add(String.valueOf(dosTerciosPrecio));
            precios.add(String.valueOf(mayorPrecio));
        }

        return precios;
    }

    public List<String> formatearPrecios(List<String> filtrosPreciosAMostrar) {
        List<String> preciosFormateados = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#");

        for (String precioStr : filtrosPreciosAMostrar) {
            try {
                Double precio = Double.parseDouble(precioStr);
                String precioFormateado = df.format(precio);
                preciosFormateados.add(precioFormateado);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return preciosFormateados;
    }

    @Override
    public List<Marca> consultarMarcas(List<SupermercadoProducto> productosFiltrados) {
        List<Marca> marcas = new ArrayList<>();
        for (SupermercadoProducto supermercadoProducto : productosFiltrados) {
            Marca marca = supermercadoProducto.getProducto().getMarca();
            if (!marcas.contains(marca)) {
                marcas.add(marca);
            }
        }
        return marcas;
    }

    @Override
    public List<SupermercadoProducto> buscarProductoACompararId(Integer id) {
        List<SupermercadoProducto> productos = new ArrayList<>();
        return productos = repositorioSupermercadoProducto.buscarProducto(id);

    }

    @Override
    public SupermercadoProducto buscarProductoIdElegido(Integer id, Integer idSupermercado) {
        return repositorioSupermercadoProducto.buscarSupermercadoProducto(id, idSupermercado);
    }


    private double getPrecioConDescuento(SupermercadoProducto producto) {
        if (producto.getDescuento() != null) {
            return producto.getPrecio() * producto.getDescuento();
        }
        return producto.getPrecio();
    }
}