package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioAdministrador {
    List<SupermercadoProducto> buscarProductosDeUnSupermercado(Integer idSupermercado);

    void guardarCombo(Combo combo);

    SupermercadoProducto buscarSupermercadoProducto(Integer productoId, Integer idSupermercado) throws SinIdProductoException;

    Combo crearCombo(SupermercadoProducto supermercadoProducto, String fechaInicio, String fechaFin, Integer cantidadVendida, Integer cantidadCobrada) throws CantidadInvalidaException, CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException, FechaInvalidaException;

    List<SupermercadoProducto> buscarProductosPorIds(List<Integer> productoIds, Integer idSupermercado) throws SinIdProductoException;

    Paquete crearPaquete(List<SupermercadoProducto> productos, String fechaInicio, String fechaFin, Double descuento, String nombre) throws FechaInvalidaException, DescuentoInvalidoException;

    void guardarPaquete(Paquete paquete);

    void actualizarPrecioYDescuento(SupermercadoProducto supermercadoProducto, Double precio, Double descuento) throws PrecioInvalidoException, DescuentoInvalidoException, SinPrecioNiDescuentoException;
}
