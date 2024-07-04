package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Paquete;
import com.tallerwebi.dominio.Promocion;
import com.tallerwebi.dominio.RepositorioPromocion;
import com.tallerwebi.dominio.SupermercadoProducto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("RepositorioPromocion")
public class RepositorioPromocionImpl implements RepositorioPromocion {

    private SessionFactory sessionFactory;

    public RepositorioPromocionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Promocion> obtenerPromociones() {
        List<Promocion> promociones =  this.sessionFactory.getCurrentSession()
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
    public void guardarPromocion(Promocion promocion){
        this.sessionFactory.getCurrentSession().save(promocion);
    }

    @Override
    public Promocion buscarPromocion(Integer idPromocion){

        String hql = "FROM Promocion WHERE idPromocion = :idPromocion";
        javax.persistence.Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idPromocion", idPromocion );
        return (Promocion) query.getSingleResult();

    }


}
