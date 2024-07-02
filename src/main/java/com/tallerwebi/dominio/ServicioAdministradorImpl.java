package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service("servicioAdministrador")
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador{

    private RepositorioAdministrador repositorioAdministrador;

    @Autowired
    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador){
        this.repositorioAdministrador = repositorioAdministrador;
    }
    @Override
    public List<SupermercadoProducto> buscarProductosDeUnSupermercado(Integer idSupermercado) {
        return this.repositorioAdministrador.obtenerProductosDeUnSupermercado(idSupermercado);
    }

    @Override
    public void guardarCombo(Combo combo) {
        this.repositorioAdministrador.guardarCombo(combo);
    }

    @Override
    public SupermercadoProducto buscarSupermercadoProducto(Integer productoId, Integer idSupermercado) throws SinIdProductoException {
        if (productoId == null){
            throw new SinIdProductoException();
        }
        return this.repositorioAdministrador.buscarSupermercadoProducto(productoId, idSupermercado);
    }

    @Override
    public Combo crearCombo(SupermercadoProducto supermercadoProducto, String fechaInicio, String fechaFin, Integer cantidadVendida, Integer cantidadCobrada) throws CantidadInvalidaException, CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException, FechaInvalidaException {

        if (cantidadVendida <= 1 || cantidadCobrada <= 0) {
            throw new CantidadInvalidaException();
        } else if (cantidadVendida <= cantidadCobrada) {
            throw new CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException();
        }

        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);

        if (fechaFinDate.isBefore(fechaInicioDate) || fechaFinDate.isEqual(fechaInicioDate)) {
            throw new FechaInvalidaException();
        }

        return new Combo(
                supermercadoProducto.getPrecio() * cantidadCobrada,
                fechaInicioDate,
                fechaFinDate,
                supermercadoProducto,
                cantidadVendida,
                cantidadCobrada
        );

    }

    @Override
    public List<SupermercadoProducto> buscarProductosPorIds(List<Integer> productoIds, Integer idSupermercado) throws SinIdProductoException {
        if (productoIds == null){
            throw new SinIdProductoException();
        }
        return this.repositorioAdministrador.obtenerProductosPorIds(productoIds, idSupermercado);
    }

    @Override
    public Paquete crearPaquete(List<SupermercadoProducto> productos, String fechaInicio, String fechaFin, Double descuento, String nombre) throws FechaInvalidaException, DescuentoInvalidoException {
        LocalDate fechaInicioDate = LocalDate.parse(fechaInicio);
        LocalDate fechaFinDate = LocalDate.parse(fechaFin);

        if (fechaFinDate.isBefore(fechaInicioDate) || fechaFinDate.isEqual(fechaInicioDate)) {
            throw new FechaInvalidaException();
        }

        if (descuento > 100.0 || descuento <= 0.0){
            throw new DescuentoInvalidoException();
        }

        Double precioFinal = 0.0;
        for (SupermercadoProducto producto : productos) {
            precioFinal += producto.getPrecio();
        }
        Double descuentoAplicado = precioFinal * (descuento / 100);
        precioFinal = precioFinal - descuentoAplicado;

        return new Paquete(precioFinal, fechaInicioDate, fechaFinDate, productos, descuento, nombre);
    }

    @Override
    public void guardarPaquete(Paquete paquete) {
        this.repositorioAdministrador.guardarPaquete(paquete);
    }

    @Override
    public void actualizarPrecioYDescuento(SupermercadoProducto supermercadoProducto, Double precio, Double descuento) throws PrecioInvalidoException, DescuentoInvalidoException, SinPrecioNiDescuentoException {
        if (precio != null  && precio <= 0){
            throw new PrecioInvalidoException();
        }
        if (descuento != null && (descuento > 100.0 || descuento <= 0.0)){
            throw new DescuentoInvalidoException();
        }
        if (precio == null && descuento == null){
            throw new SinPrecioNiDescuentoException();
        }
        this.repositorioAdministrador.actualizarPrecioYDescuento(supermercadoProducto, precio, descuento);
    }

}
