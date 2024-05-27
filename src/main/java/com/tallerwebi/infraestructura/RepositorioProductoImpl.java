package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Subcategoria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
    public List<Producto> buscarProductoPorNombre(String nombre) {

        String nombreLimpio = limpiarNombre(nombre);

        String consulta ="FROM Producto p WHERE p.nombre LIKE :nombre";
        TypedQuery<Producto> query = this.sessionFactory.getCurrentSession().createQuery(consulta,Producto.class);
        query.setParameter("nombre","%"+nombreLimpio+"%");
        return query.getResultList();
        }

    private String limpiarNombre(String input) {
        // Eliminar caracteres especiales y espacios extra
        if (input != null) {
            return input.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
        }
        return "";
    }

    @Override
    public List<Producto> buscarProductosPorSubcategoria(Subcategoria subcategoria) {
        List<Producto> productosEncontrados = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE subcategoria = :subcategoriaParam")
                .setParameter("subcategoriaParam", subcategoria)
                .getResultList();
        return productosEncontrados;
    }

    @Override
    public List<Producto> buscarProductoPorCategoria(Categoria categoria) {
        List<Producto> productosEncontrados = this.sessionFactory.getCurrentSession()
                .createQuery( "FROM Producto WHERE categoria = :categoriaParam")
                .setParameter("categoriaParam",categoria)
                .getResultList();
        return productosEncontrados;
    }


}

