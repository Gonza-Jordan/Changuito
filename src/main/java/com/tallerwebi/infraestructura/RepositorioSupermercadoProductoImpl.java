package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Subcategoria;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;


@Repository("RepositorioSupermercadoProducto")
public class RepositorioSupermercadoProductoImpl implements RepositorioSupermercadoProducto {
    private SessionFactory sessionFactory;

    public RepositorioSupermercadoProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarProductoConSupermercado(Supermercado supermercado, Producto producto) {
        SupermercadoProducto supermercadoProducto = new SupermercadoProducto();
        supermercadoProducto.setProducto(producto);
        supermercadoProducto.setSupermercado(supermercado);
        this.sessionFactory.getCurrentSession().save(supermercadoProducto);
    }

    @Override
    public List<SupermercadoProducto> buscarConFiltros(String subcategoria, Map<String, List<String>> filtros, List<Integer> productoIds) {
        StringBuilder consulta = new StringBuilder("SELECT sp FROM SupermercadoProducto sp JOIN sp.producto p WHERE 1 = 1");

        if (subcategoria != null && !subcategoria.isEmpty()) {
            consulta.append(" AND p.subcategoria = :subcategoria");
        }

        if (productoIds != null && !productoIds.isEmpty()) {
            consulta.append(" AND sp.producto.idProducto IN (:productoIds)");
        }

        for (Map.Entry<String, List<String>> entry : filtros.entrySet()) {
            String nombreFiltro = entry.getKey();
            List<String> valoresFiltro = entry.getValue();

            if (valoresFiltro != null && !valoresFiltro.isEmpty()) {
                consulta.append(" AND (");
                for (int i = 0; i < valoresFiltro.size(); i++) {
                    String valor = valoresFiltro.get(i);

                    if (nombreFiltro.equals("precio")) {
                        consulta.append("sp.precio ").append(valor);
                    } else {
                        consulta.append(nombreFiltro).append(" = :").append(nombreFiltro).append(i);
                    }
                    if (i < valoresFiltro.size() - 1) {
                        consulta.append(" OR ");
                    }
                }
                consulta.append(")");
            }
        }

        Query<SupermercadoProducto> query = sessionFactory.getCurrentSession().createQuery(consulta.toString(), SupermercadoProducto.class);

        if (subcategoria != null && !subcategoria.isEmpty()) {
            query.setParameter("subcategoria", Subcategoria.valueOf(subcategoria));
        }

        if (productoIds != null && !productoIds.isEmpty()) {
            query.setParameter("productoIds", productoIds);
        }

        for (Map.Entry<String, List<String>> entry : filtros.entrySet()) {
            String nombreFiltro = entry.getKey();
            List<String> valoresFiltro = entry.getValue();

            if (valoresFiltro != null && !valoresFiltro.isEmpty()) {
                for (int i = 0; i < valoresFiltro.size(); i++) {
                    String valor = valoresFiltro.get(i);

                    if (!nombreFiltro.equals("precio")) {
                        query.setParameter(nombreFiltro + i, valor);
                    }
                }
            }
        }
        return query.getResultList();
    }

}


