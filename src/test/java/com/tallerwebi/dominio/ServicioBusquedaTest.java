package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioProductoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBusquedaTest {
    private ServicioBusqueda servicioBusqueda;
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    public void init(){
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.servicioBusqueda = new ServicioBusquedaImpl(this.repositorioProducto);
    }

    @Test
    public void queSePuedaConsultarUnProductoPorNombre(){
        //Preparacion
        Producto productoMock = new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg");
        when(this.repositorioProducto.buscarProductoPorNombre(productoMock.getNombre())).thenReturn(productoMock);

        //Ejecucion
        Producto productoObtenido = this.servicioBusqueda.consultarProductoPorNombre(productoMock.getNombre());

        //Verficacion
        assertThat(productoObtenido, equalTo(productoMock));
    }

    @Test
    public void queSePuedanConsultarTodosLosProductosDeUnaSubcategoria(){
        //Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg"));
        productosMock.add(new Producto("Sprite", 1500.00, "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/sprite.jpg"));
        when(this.repositorioProducto.buscarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(productosMock);

        //Ejecucion
        List<Producto> productosObtenidos = this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas);

        //Verficacion
        assertThat(productosObtenidos, equalTo(productosMock));
    }

}
