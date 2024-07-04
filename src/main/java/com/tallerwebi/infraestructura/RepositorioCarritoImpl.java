package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.RepositorioCarrito;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;

@Repository("repositorioCarrito")
public class RepositorioCarritoImpl implements RepositorioCarrito {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCarritoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Carrito carrito) {

//        usuario.setDni( (Integer) usuario.getDni());

        sessionFactory.getCurrentSession().save(carrito);
    }


    @Override
    public Carrito buscar(Date stamp) {
        String hql = "FROM Carrito WHERE fechaDeCreacion = :stamp";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("stamp", stamp);

        try {
            return (Carrito) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Carrito buscarPorId(Long id) {
        String hql = "FROM Carrito WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);

        try {
            return (Carrito) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    // Para update necesito buscar primero buscar en la bd y modificar ese objeto, no puedo modificar el que traigo de la web
    @Override
    public void modificar(Carrito carrito) {
//        Carrito carritoEncontrado = this.buscar(carrito.getFechaDeCreacion());
//        sessionFactory.getCurrentSession().merge(carritoEncontrado);

        Carrito carritoEncontrado = sessionFactory.getCurrentSession().find(Carrito.class, carrito.getId());
        if (carritoEncontrado != null) {
            carritoEncontrado.setSupermercadoProducto(carrito.getSupermercadoProducto());
            carritoEncontrado.setPromocion(carrito.getPromocion());

            sessionFactory.getCurrentSession().merge(carritoEncontrado);
        }

    }

    @Override
    public void eliminar(Carrito carrito) {

        Carrito carritoEncontrado = sessionFactory.getCurrentSession().find(Carrito.class, carrito.getId());
        if (carritoEncontrado != null) {
            sessionFactory.getCurrentSession().delete(carritoEncontrado);
        }

    }





}