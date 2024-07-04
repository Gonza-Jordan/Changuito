//package com.tallerwebi.infraestructura;
//
//import com.tallerwebi.dominio.Producto;
//import com.tallerwebi.dominio.RepositorioResenia;
//import com.tallerwebi.dominio.Resenia;
//import org.hibernate.SessionFactory;
//import org.springframework.stereotype.Repository;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//
//@Repository("repositorioResenia")
//
//public class RepositorioReseniaImpl implements RepositorioResenia {
//
//    private final SessionFactory sessionFactory;
//
//    public RepositorioReseniaImpl(SessionFactory sessionFactory) {
//
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    @Transactional
//    public List<Resenia> listarResenias(Integer idProducto) {
//        return this.sessionFactory.getCurrentSession()
//                .createQuery("FROM Resenia WHERE idProducto =:idProducto ", Resenia.class)
//                .setParameter("idProducto", idProducto)
//                .getResultList();
//
//    }
//
//    @Override
//    @Transactional
//    public void agregarResenia(Resenia resenia) {
//        this.sessionFactory.getCurrentSession().save(resenia);
//
//    }
//}
