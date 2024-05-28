package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.RepositorioSupermercado;
import com.tallerwebi.dominio.Supermercado;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("RepositorioSupermercado")
public class RepositorioSupermercadoImpl implements RepositorioSupermercado {
    private SessionFactory sessionFactory;

    public RepositorioSupermercadoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarSupermercado(Supermercado supermercado) {
        this.sessionFactory.getCurrentSession().save(supermercado);
    }

    @Override
    public void guardarProductoConSupermercado(Supermercado supermercado, Producto producto) {
        producto.setSupermercado(supermercado);
        this.sessionFactory.getCurrentSession().save(producto);
    }
}
