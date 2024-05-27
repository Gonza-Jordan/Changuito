package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.Subcategoria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;


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


    @Override
    public List<Producto> buscarProductosConFiltros(String subcategoriaStr, Map<String, List<String>> filtros) {
        StringBuilder consulta = new StringBuilder("FROM Producto p WHERE 1 = 1");

        // Añade la subcategoría a la consulta
        if (subcategoriaStr != null && !subcategoriaStr.isEmpty()) {
            consulta.append(" AND subcategoria = :subcategoria");
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

        // Setea el parámetro de subcategoría
        if (subcategoriaStr != null && !subcategoriaStr.isEmpty()) {
            query.setParameter("subcategoria", Subcategoria.valueOf(subcategoriaStr));
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
                    } else {
                        query.setParameter(nombreFiltro + i, valor);
                    }
                }
            }
        }

        List<Producto> productosFiltrados = query.getResultList();
        return productosFiltrados;
    }


}

