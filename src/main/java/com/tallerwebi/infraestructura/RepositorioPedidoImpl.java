package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioCarrito;
import com.tallerwebi.dominio.RepositorioPedido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;

@Repository("repositorioPedido")
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
    }


}