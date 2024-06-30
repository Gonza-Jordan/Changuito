package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantidadInvalidaException;
import com.tallerwebi.dominio.excepcion.CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException;
import com.tallerwebi.dominio.excepcion.FechaInvalidaException;
import com.tallerwebi.dominio.excepcion.SinIdProductoException;

import java.util.List;

public interface ServicioAdministrador {
    List<SupermercadoProducto> buscarProductosDeUnSupermercado(Integer idSupermercado);

    void guardarCombo(Combo combo);

    SupermercadoProducto buscarSupermercadoProducto(Integer productoId, Integer idSupermercado) throws SinIdProductoException;

    Combo crearCombo(SupermercadoProducto supermercadoProducto, String fechaInicio, String fechaFin, Integer cantidadVendida, Integer cantidadCobrada) throws CantidadInvalidaException, CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException, FechaInvalidaException;

    List<SupermercadoProducto> buscarProductosPorIds(List<Integer> productoIds, Integer idSupermercado) throws SinIdProductoException;

    Paquete crearPaquete(List<SupermercadoProducto> productos, String fechaInicio, String fechaFin, Double descuento, String nombre) throws FechaInvalidaException;

    void guardarPaquete(Paquete paquete);
}
