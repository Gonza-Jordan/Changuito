package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Subcategoria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;


@Repository("RepositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {
    private SessionFactory sessionFactory;

    public RepositorioProductoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarProducto(Producto producto) {
        this.sessionFactory.getCurrentSession().save(producto);
    }


    @Override
    public Producto buscarProductoPorNombre(String nombre) {
        return (Producto) this.sessionFactory.getCurrentSession().createCriteria(Producto.class)
                .add(Restrictions.eq("nombre",nombre))
                .uniqueResult();
        }

    @Override
    public List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria) {
        List<Producto> productosEncontrados = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE subcategoria = :subcategoriaParam")
                .setParameter("subcategoriaParam", subcategoria)
                .getResultList();
        return productosEncontrados;
    }


}

