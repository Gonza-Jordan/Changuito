package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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

    @Override
    public void actualizarPrecioYDescuento(SupermercadoProducto supermercadoProducto, Double precio, Double descuento) {

        if (descuento != null) {
            descuento = descuento / 100;
            descuento = 1.0 - descuento;
        }

        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder("UPDATE SupermercadoProducto sp SET ");
        boolean commaNeeded = false;

        if (precio != null) {
            queryBuilder.append("sp.precio = :precio ");
            commaNeeded = true;
        }
        if (descuento != null) {
            if (commaNeeded) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("sp.descuento = :descuento ");
        }
        queryBuilder.append("WHERE sp.id = :id");

        Query query = session.createQuery(queryBuilder.toString());

        if (precio != null) {
            query.setParameter("precio", precio);
        }
        if (descuento != null) {
            query.setParameter("descuento", descuento);
        }
        query.setParameter("id", supermercadoProducto.getId());

        query.executeUpdate();
    }

}



