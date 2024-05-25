package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorProductoBuscadoTest {

    private ServicioBusqueda servicioBusqueda;
    private ControladorProductoBuscado controladorProductoBuscado;

    @BeforeEach
    public void init(){
        this.servicioBusqueda = mock(ServicioBusqueda.class);
        this.controladorProductoBuscado = new ControladorProductoBuscado(this.servicioBusqueda);
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestrenLosProductosDelTipoGaseosas(){
        //Preparacion
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(new Producto("Coca Cola", 2000.00, "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "img/producto/bebidas/coca_cola.jpg"));
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(productosMock);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.irAProductoBuscado(Subcategoria.Gaseosas);

        //Validacion
        assertThat(mav.getModel().get("productos"), equalTo(productosMock));
    }

    @Test
    public void queAlHacerClickEnLaSubcategoriaGaseosasSeMuestreUnMensajeDeErrorPorqueNoEncontroProductosDeEsaSubcategoria(){
        //Preparacion
        when(this.servicioBusqueda.consultarProductosPorSubcategoria(Subcategoria.Gaseosas)).thenReturn(null);

        //Ejecucion
        ModelAndView mav = this.controladorProductoBuscado.irAProductoBuscado(Subcategoria.Gaseosas);

        //Validacion
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat(mav.getModel().get("error"), is("Productos de esa subcategoria no encontrados"));
    }



}
