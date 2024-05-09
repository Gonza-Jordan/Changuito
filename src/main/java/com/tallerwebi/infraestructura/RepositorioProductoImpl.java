package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.RepositorioProducto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("RepositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl(SessionFactory sessionFactory){

        this.sessionFactory = sessionFactory;
    }



    @Override
    public Producto buscarProducto(String nombre) {
        final Session session = sessionFactory.getCurrentSession();

        return (Producto) session.createCriteria(Producto.class)
                .add(Restrictions.eq("nombre",nombre))
                .uniqueResult();

        }

}

