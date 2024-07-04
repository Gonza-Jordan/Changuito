//package com.tallerwebi.dominio;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Service ("ServicioResenia")
//@Transactional
//public class ServicioReseniaImpl implements ServicioResenia {
//
//    private final RepositorioResenia repositorioResenia;
//
//    @Autowired
//    public ServicioReseniaImpl(RepositorioResenia repositorioResenia) {
//        this.repositorioResenia = repositorioResenia;
//    }
//
//
//
//    @Override
//    public void guardarResenia(Resenia resenia) {
//        repositorioResenia.agregarResenia(resenia);
//    }
//
//    @Override
//    public List<Resenia> obtenerResenias(Integer idProducto) {
//        return repositorioResenia.listarResenias(idProducto);
//    }
//}
