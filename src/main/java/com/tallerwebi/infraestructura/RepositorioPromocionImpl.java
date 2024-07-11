package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository("RepositorioPromocion")
public class RepositorioPromocionImpl implements RepositorioPromocion {

    private SessionFactory sessionFactory;

    public RepositorioPromocionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Promocion> obtenerPromociones() {
        List<Promocion> promociones = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Promocion WHERE 1 = 1", Promocion.class)
                .getResultList();
        for (Promocion promocion : promociones) {
            if (promocion instanceof Paquete) {
                Paquete paquete = (Paquete) promocion;
                paquete.getProductos().size();  //Fuerzo la inicialización
            }
        }
        return promociones;
    }

    @Override
    public List<Promocion> obtenerPromocionesDeProducto(Producto producto) {

        String hql = "FROM Promocion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        List<Promocion> promociones = query.getResultList();

        List<Promocion> promoConProductos = new ArrayList<>();
        for (Promocion promocion : promociones) {
            if (promocion instanceof Paquete) {
                Paquete paquete = (Paquete) promocion;
                List<SupermercadoProducto> supermercadosProductos = paquete.getProductos();
                supermercadosProductos.forEach(supermercadoProducto -> {
                    if (Objects.equals(supermercadoProducto.getProducto().getIdProducto(), producto.getIdProducto())) {
                        promoConProductos.add(promocion);
                    }
                });
            } else {
                Combo combo = (Combo) promocion;
                if (Objects.equals(combo.getProducto().getProducto().getIdProducto(), producto.getIdProducto())) {
                    promoConProductos.add(promocion);
                }

            }
        }

        return promoConProductos;
    }


    @Override
    public List<Promocion> obtenerTodasLasPromociones() {

        String hql = "FROM Promocion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        List<Promocion> promociones = query.getResultList();

        return promociones;
    }

    @Override
    public void guardarPromocion(Promocion promocion) {
        this.sessionFactory.getCurrentSession().save(promocion);
    }

    @Override
    public Promocion buscarPromocion(Integer idPromocion) {

        String hql = "FROM Promocion WHERE idPromocion = :idPromocion";
        javax.persistence.Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idPromocion", idPromocion);
        return (Promocion) query.getSingleResult();

    }


}
