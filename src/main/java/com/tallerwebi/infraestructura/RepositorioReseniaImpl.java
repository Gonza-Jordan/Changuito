package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioResenia;
import com.tallerwebi.dominio.Resenia;
import org.hibernate.SessionFactory;

import java.util.List;

public class RepositorioReseniaImpl implements RepositorioResenia {

    private final SessionFactory sessionFactory;

    public RepositorioReseniaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Resenia> listarResenias(Integer idProducto) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Resenia WHERE idProducto =:idProducto ", Resenia.class)
                .setParameter("idProducto", idProducto)
                .getResultList();

    }

    @Override
    public void agregarResenia(Resenia resenia) {
        this.sessionFactory.getCurrentSession().save(resenia);

    }
}
