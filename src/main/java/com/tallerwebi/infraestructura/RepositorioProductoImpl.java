package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("RepositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {
    private SessionFactory sessionFactory;

    public RepositorioProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarProducto(Producto producto) {
        this.sessionFactory.getCurrentSession().save(producto);
    }

    @Override
    public List<Producto> buscarProductoPorNombre(String nombre) {
        String nombreLimpio = limpiarNombre(nombre).toLowerCase();
        String consulta = "FROM Producto p WHERE lower(p.nombre) LIKE :nombre";
        TypedQuery<Producto> query = this.sessionFactory.getCurrentSession().createQuery(consulta, Producto.class);
        query.setParameter("nombre", "%" + nombreLimpio + "%");
        return query.getResultList();
    }

    private String limpiarNombre(String input) {
        if (input != null) {
            return input.trim().replaceAll("(?m)^[\\s\\h]+|[\\s\\h]+$", "").replaceAll("[^a-zA-Z0-9\\s]", "");
        }
        return "";
    }

    @Override
    public List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE subcategoria = :subcategoriaParam", Producto.class)
                .setParameter("subcategoriaParam", subcategoria)
                .getResultList();
    }

    @Override
    public List<Producto> buscarProductoPorCategoria(Categoria categoria) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE categoria = :categoriaParam", Producto.class)
                .setParameter("categoriaParam", categoria)
                .getResultList();
    }

    @Override
    public List<Producto> buscarProductosPorIds(List<Integer> ids) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT DISTINCT sp.producto FROM SupermercadoProducto sp WHERE sp.producto.idProducto IN (:ids)", Producto.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public Producto buscarProductoPorId(Integer id) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("SELECT p FROM Producto p WHERE p.idProducto =:id ", Producto.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    @Override
    public Producto buscarPorId(Integer idProducto) {
        String hql = "FROM Producto WHERE idProducto = :idProducto";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idProducto", idProducto);

        try {
            return (Producto) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        TypedQuery<Producto> query = sessionFactory.getCurrentSession().createQuery("from Producto", Producto.class);
        return query.getResultList();
    }

}


