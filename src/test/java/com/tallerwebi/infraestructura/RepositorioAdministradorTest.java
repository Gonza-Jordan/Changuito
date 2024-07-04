package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class RepositorioAdministradorTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioAdministrador repositorioAdministrador;
    private RepositorioSupermercadoProducto repositorioSupermercadoProducto;
    private RepositorioProducto repositorioProducto;
    private RepositorioSupermercado repositorioSupermercado;

    private Producto productoMock;
    private Producto otroProductoMock;
    private Supermercado supermercadoMock;
    private Marca marcaCocaCola;


    @BeforeEach
    public void init() {
        this.repositorioAdministrador = new RepositorioAdministradorImpl(this.sessionFactory);
        this.repositorioSupermercadoProducto = new RepositorioSupermercadoProductoImpl(this.sessionFactory);
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.repositorioSupermercado = mock(RepositorioSupermercado.class);
        this.marcaCocaCola = new Marca("Coca Cola");

        this.sessionFactory.getCurrentSession().save(marcaCocaCola);
        productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaCocaCola);
        otroProductoMock = new Producto("Sprite", "123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "", marcaCocaCola);
        supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "");

        this.repositorioProducto.guardarProducto(productoMock);
        this.repositorioProducto.guardarProducto(otroProductoMock);
        this.repositorioSupermercado.guardarSupermercado(supermercadoMock);

        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, productoMock);
        this.repositorioSupermercadoProducto.guardarSupermercadoProducto(supermercadoMock, otroProductoMock);

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerProductosDeUnSupermercado() {
        //Preparacion
        Integer idSupermercado = 1;

        //Ejecucion
        List<SupermercadoProducto> productosObtenidos = this.repositorioAdministrador.obtenerProductosDeUnSupermercado(idSupermercado);

        //Verficacion
        assertThat(productosObtenidos, is(notNullValue()));

        List<Integer> supermercadoIds = productosObtenidos.stream()
                .map(producto -> producto.getSupermercado().getIdSupermercado())
                .collect(Collectors.toList());

        assertThat(supermercadoIds, everyItem(equalTo(idSupermercado)));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnCombo() {
        //Preparacion
        Combo comboAGuardar = mock(Combo.class);

        //Ejecucion
        this.repositorioAdministrador.guardarCombo(comboAGuardar);

        //Verficacion
        Combo comboObtenido = (Combo) this.sessionFactory.getCurrentSession()
                .createQuery("FROM Combo WHERE 1 = 1")
                .getSingleResult();

        assertThat(comboObtenido, equalTo(comboAGuardar));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnSupermercadoProducto() {
        //Preparacion
        Integer idSupermercado = supermercadoMock.getIdSupermercado();
        Integer idProducto = productoMock.getIdProducto();

        //Ejecucion
        SupermercadoProducto supermercadoProducto = this.repositorioAdministrador.buscarSupermercadoProducto(idProducto, idSupermercado);

        //Verficacion
        assertThat(supermercadoProducto.getProducto(), equalTo(productoMock));
        assertThat(supermercadoProducto.getSupermercado(), equalTo(supermercadoMock));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerProductosPorIds() {
        //Preparacion
        Integer idSupermercado = supermercadoMock.getIdSupermercado();
        Integer idProducto = productoMock.getIdProducto();
        Integer otroIdProducto = otroProductoMock.getIdProducto();

        List<Integer> idList = new ArrayList<>();
        idList.add(idProducto);
        idList.add(otroIdProducto);

        //Ejecucion
        List<SupermercadoProducto> supermercadosProducto = this.repositorioAdministrador.obtenerProductosPorIds(idList, idSupermercado);

        //Verficacion
        assertThat(supermercadosProducto.get(0).getProducto(), equalTo(productoMock));
        assertThat(supermercadosProducto.get(1).getProducto(), equalTo(otroProductoMock));
        assertThat(supermercadosProducto.get(0).getSupermercado(), equalTo(supermercadoMock));
        assertThat(supermercadosProducto.get(1).getSupermercado(), equalTo(supermercadoMock));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnPaquete() {
        //Preparacion
        Paquete paqueteAGuardar = mock(Paquete.class);

        //Ejecucion
        this.repositorioAdministrador.guardarPaquete(paqueteAGuardar);

        //Verficacion
        Paquete paqueteObtenido = (Paquete) this.sessionFactory.getCurrentSession()
                .createQuery("FROM Paquete WHERE 1 = 1")
                .getSingleResult();

        assertThat(paqueteObtenido, equalTo(paqueteAGuardar));

    }


}
