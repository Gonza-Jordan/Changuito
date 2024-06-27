package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void modificarPedidoCarrito(Usuario usuario) {

//        //SOLUCION PARA ERROR MULTIPLE ENTITIES (SI USUARIO ESTA EN LA SESION NO HACE FALTA HACER MERGE)
//        Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, this.buscar(usuario.getEmail()).getId());
//
//        if (existingUsuario != null) {
//            existingUsuario.setNombre(usuario.getNombre());
//            existingUsuario.setApellido(usuario.getApellido());
//            existingUsuario.setDireccion(usuario.getDireccion());
//            existingUsuario.setContrasena(usuario.getContrasena());
//            existingUsuario.setAdmin(usuario.getAdmin());
//            existingUsuario.setStampCarritoActivo(usuario.getStampCarritoActivo());
//
//            if (usuario.getCarritos() != null) {
//                existingUsuario.getCarritos().addAll(usuario.getCarritos());
//            }
//
//            if (usuario.getPedidos() != null) {
//                existingUsuario.getPedidos().addAll(usuario.getPedidos());
//            }
//
//            sessionFactory.getCurrentSession().merge(existingUsuario);
//
//        }

        //sessionFactory.getCurrentSession().evict(usuario);
        //SOLUCION PARA ERROR MULTIPLE ENTITIES (SI USUARIO ESTA EN LA SESION NO HACE FALTA HACER MERGE)
        if (sessionFactory.getCurrentSession().contains(this.buscar(usuario.getEmail()))) {
            Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, this.buscar(usuario.getEmail()).getId());


            if (existingUsuario != null) {

                if (usuario.getCarritos()!=null){
                    existingUsuario.getCarritos().clear();
                    existingUsuario.getCarritos().addAll(usuario.getCarritos());
                }

                if (usuario.getPedidos()!=null){
                    existingUsuario.getPedidos().clear();
                    existingUsuario.getPedidos().addAll(usuario.getPedidos());
                }

                sessionFactory.getCurrentSession().merge(existingUsuario);

            }
        }
//        else {
//
//            if (usuario.getCarritos() != null) {
//                usuario.getCarritos().clear();
//                usuario.getCarritos().addAll(usuario.getCarritos());
//            }
//
//            if (usuario.getPedidos() != null) {
//                usuario.getPedidos().clear();
//                usuario.getPedidos().addAll(usuario.getPedidos());
//            }
//        }

    }

    // Para update necesito buscar primero buscar en la bd y modificar ese objeto, no puedo modificar el que traigo de la web
    @Override
    public void modificar(Usuario usuario) {

//        //SOLUCION PARA ERROR MULTIPLE ENTITIES (SI USUARIO ESTA EN LA SESION NO HACE FALTA HACER MERGE)
//        Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, this.buscar(usuario.getEmail()).getId());
//
//        if (existingUsuario != null) {
//            existingUsuario.setNombre(usuario.getNombre());
//            existingUsuario.setApellido(usuario.getApellido());
//            existingUsuario.setDireccion(usuario.getDireccion());
//            existingUsuario.setContrasena(usuario.getContrasena());
//            existingUsuario.setAdmin(usuario.getAdmin());
//            existingUsuario.setStampCarritoActivo(usuario.getStampCarritoActivo());
//
//            if (usuario.getCarritos() != null) {
//                existingUsuario.getCarritos().addAll(usuario.getCarritos());
//            }
//
//            if (usuario.getPedidos() != null) {
//                existingUsuario.getPedidos().addAll(usuario.getPedidos());
//            }
//
//            sessionFactory.getCurrentSession().merge(existingUsuario);
//
//        }


        //SOLUCION PARA ERROR MULTIPLE ENTITIES (SI USUARIO ESTA EN LA SESION NO HACE FALTA HACER MERGE)
        if (sessionFactory.getCurrentSession().contains(this.buscar(usuario.getEmail()))) {
            Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, this.buscar(usuario.getEmail()).getId());


            if (existingUsuario != null) {
                existingUsuario.setNombre(usuario.getNombre());
                existingUsuario.setApellido(usuario.getApellido());
                existingUsuario.setDireccion(usuario.getDireccion());
                existingUsuario.setContrasena(usuario.getContrasena());
                existingUsuario.setAdmin(usuario.getAdmin());
                existingUsuario.setStampCarritoActivo(usuario.getStampCarritoActivo());


                sessionFactory.getCurrentSession().merge(existingUsuario);

            }
        }else {
            usuario.setNombre(usuario.getNombre());
            usuario.setApellido(usuario.getApellido());
            usuario.setDireccion(usuario.getDireccion());
            usuario.setContrasena(usuario.getContrasena());
            usuario.setAdmin(usuario.getAdmin());
            usuario.setStampCarritoActivo(usuario.getStampCarritoActivo());

        }

    }

    @Override
    public void eliminarCarritoDeUsuario(Usuario usuario, Carrito carrito) {

//        String hql = "DELETE FROM Carrito WHERE id = :id";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("id", carrito.getId());
//        query.executeUpdate();


        String hql1 = "FROM Usuario WHERE id = :usuarioId";
        Query query1 = this.sessionFactory.getCurrentSession().createQuery(hql1);
        query1.setParameter("usuarioId", usuario.getId());
        Usuario usuario1 = (Usuario) query1.getSingleResult();

        List<Carrito> carritosUsuario = usuario1.getCarritos();

        carritosUsuario.removeIf(carritoFor -> Objects.equals(carritoFor.getId(), carrito.getId()));

        Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, usuario.getId());
        if (existingUsuario != null) {
            existingUsuario.setCarritos(carritosUsuario);
            sessionFactory.getCurrentSession().merge(existingUsuario);
        }

    }


}

