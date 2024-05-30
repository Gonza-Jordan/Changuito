package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Supermercado;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Usuario usuario) {

//        usuario.setDni( (Integer) usuario.getDni());

        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        String hql = "FROM Usuario WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    // Para update necesito buscar primero buscar en la bd y modificar ese objeto, no puedo modificar el que traigo de la web
    @Override
    public void modificar(Usuario usuario) {
        Usuario usarioEncoantrado = this.buscar(usuario.getEmail());
        sessionFactory.getCurrentSession().merge(usarioEncoantrado);
    }


}