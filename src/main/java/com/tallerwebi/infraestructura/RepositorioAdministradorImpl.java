package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository("RepositorioAdministrador")
public class RepositorioAdministradorImpl implements RepositorioAdministrador {
    private SessionFactory sessionFactory;

    public RepositorioAdministradorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<SupermercadoProducto> obtenerProductosDeUnSupermercado(Integer idSupermercado) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT sp FROM SupermercadoProducto sp JOIN sp.supermercado s WHERE s.id = :idSupermercado", SupermercadoProducto.class)
                .setParameter("idSupermercado", idSupermercado)
                .getResultList();
    }

    @Override
    public void guardarCombo(Combo combo) {
        this.sessionFactory.getCurrentSession().save(combo);
    }

    @Override
    public SupermercadoProducto buscarSupermercadoProducto(Integer productoId, Integer idSupermercado) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT sp FROM SupermercadoProducto sp JOIN sp.supermercado s JOIN sp.producto p WHERE s.id = :idSupermercado AND p.id = :productoId", SupermercadoProducto.class)
                .setParameter("idSupermercado", idSupermercado)
                .setParameter("productoId", productoId)
                .getSingleResult();
    }

    @Override
    public List<SupermercadoProducto> obtenerProductosPorIds(List<Integer> productoIds, Integer idSupermercado) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT sp FROM SupermercadoProducto sp JOIN sp.supermercado s JOIN sp.producto p WHERE s.id = :idSupermercado AND p.id IN (:productoIds)", SupermercadoProducto.class)
                .setParameter("idSupermercado", idSupermercado)
                .setParameterList("productoIds", productoIds)
                .getResultList();
    }

    @Override
    public void guardarPaquete(Paquete paquete) {
        this.sessionFactory.getCurrentSession().save(paquete);
    }

}



