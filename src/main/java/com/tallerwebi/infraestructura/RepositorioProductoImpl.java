package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Subcategoria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.xml.bind.DatatypeConverter.parseInteger;


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
        String nombreLimpio = limpiarNombre(nombre).toLowerCase(); // Convertir a minúsculas
        String consulta = "FROM Producto p WHERE lower(p.nombre) LIKE :nombre"; // Convertir a minúsculas en la consulta
        TypedQuery<Producto> query = this.sessionFactory.getCurrentSession().createQuery(consulta, Producto.class);
        query.setParameter("nombre", "%" + nombreLimpio + "%");
        return query.getResultList();
    }

    private String limpiarNombre(String input) {
        if (input != null) {
            // Eliminar caracteres especiales y espacios adicionales al principio y al final de la cadena
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
    public List<Producto> buscarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros, String productoIds) {
        StringBuilder consulta = new StringBuilder("FROM Producto p WHERE 1 = 1");

        if (subcategoriaStr != null && !subcategoriaStr.isEmpty()) {
            consulta.append(" AND subcategoria = :subcategoria");
        }

        // Añade los IDs de los productos a la consulta
        if (productoIds != null && !productoIds.isEmpty()) {
            String[] idsArray = productoIds.split(",");
            if (idsArray.length > 0) {
                consulta.append(" AND (");
                for (int i = 0; i < idsArray.length; i++) {
                    consulta.append("p.idProducto = :idProducto").append(i);
                    if (i < idsArray.length - 1) {
                        consulta.append(" OR ");
                    }
                }
                consulta.append(")");
            }
        }


        for (Map.Entry<String, List<String>> entry : filtros.entrySet()) {
            String nombreFiltro = entry.getKey();
            List<String> valoresFiltro = entry.getValue();

            if (valoresFiltro != null && !valoresFiltro.isEmpty()) {
                consulta.append(" AND (");

                for (int i = 0; i < valoresFiltro.size(); i++) {
                    String valor = valoresFiltro.get(i);

                    if (nombreFiltro.equals("precio")) {
                        consulta.append(nombreFiltro).append(" ").append(valor);
                    } else if (nombreFiltro.equals("descuento")) {
                        consulta.append("p.descuento = p.precio * :factor").append(i);
                    } else if (nombreFiltro.equals("supermercado_id")) {
                        consulta.append("p.supermercado.idSupermercado = :supermercadoId").append(i);
                    } else {
                        consulta.append("p.").append(nombreFiltro).append(" = :").append(nombreFiltro).append(i);
                    }

                    if (i < valoresFiltro.size() - 1) {
                        consulta.append(" OR ");
                    }
                }

                consulta.append(")");
            }
        }

        Query query = this.sessionFactory.getCurrentSession().createQuery(consulta.toString(), Producto.class);

        if (subcategoriaStr != null && !subcategoriaStr.isEmpty()) {
            query.setParameter("subcategoria", Subcategoria.valueOf(subcategoriaStr));
        }

        // Setea los parámetros de IDs de productos
        if (productoIds != null && !productoIds.isEmpty()) {
            String[] idsArray = productoIds.split(",");
            for (int i = 0; i < idsArray.length; i++) {
                query.setParameter("idProducto" + i, Integer.parseInt(idsArray[i]));
            }
        }

        for (Map.Entry<String, List<String>> entry : filtros.entrySet()) {
            String nombreFiltro = entry.getKey();
            List<String> valoresFiltro = entry.getValue();

            if (valoresFiltro != null && !valoresFiltro.isEmpty()) {
                for (int i = 0; i < valoresFiltro.size(); i++) {
                    String valor = valoresFiltro.get(i);

                    if (nombreFiltro.equals("precio")) {
                        if (!valor.contains("BETWEEN") && !valor.contains(">") && !valor.contains("<")) {
                            query.setParameter(nombreFiltro + i, Double.parseDouble(valor));
                        }
                    } else if (nombreFiltro.equals("descuento")) {
                        query.setParameter("factor" + i, Double.parseDouble(valor));
                    } else if (nombreFiltro.equals("supermercado_id")) {
                        query.setParameter("supermercadoId" + i, Integer.parseInt(valor));
                    } else {
                        query.setParameter(nombreFiltro + i, valor);
                    }
                }
            }
        }

        return query.getResultList();
    }

    @Override
    public List<Producto> buscarProductosPorIds(List<Integer> ids) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Producto WHERE idProducto IN (:ids)", Producto.class)
                .setParameter("ids", ids)
                .getResultList();
    }

}

