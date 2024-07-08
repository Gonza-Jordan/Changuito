package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.RepositorioUsuario;
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
        if (sessionFactory.getCurrentSession().contains(this.buscar(usuario.getEmail()))) {
            Usuario existingUsuario = sessionFactory.getCurrentSession().find(Usuario.class, this.buscar(usuario.getEmail()).getId());

            if (existingUsuario != null) {
                if (usuario.getCarritos() != null) {
                    existingUsuario.getCarritos().clear();
                    existingUsuario.getCarritos().addAll(usuario.getCarritos());
                }

                if (usuario.getPedidos() != null) {
                    existingUsuario.getPedidos().clear();
                    existingUsuario.getPedidos().addAll(usuario.getPedidos());
                }

                sessionFactory.getCurrentSession().merge(existingUsuario);
            }
        }
    }

    @Override
    public void modificar(Usuario usuario) {
        Usuario existingUsuario = sessionFactory.getCurrentSession().get(Usuario.class, usuario.getId());

        if (existingUsuario != null) {
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setApellido(usuario.getApellido());
            existingUsuario.setDireccion(usuario.getDireccion());
            existingUsuario.setContrasena(usuario.getContrasena());
            existingUsuario.setAdmin(usuario.getAdmin());
            existingUsuario.setStampCarritoActivo(usuario.getStampCarritoActivo());

            if (usuario.getCarritos() != null) {
                existingUsuario.getCarritos().clear();
                existingUsuario.getCarritos().addAll(usuario.getCarritos());
            }

            if (usuario.getPedidos() != null) {
                existingUsuario.getPedidos().clear();
                existingUsuario.getPedidos().addAll(usuario.getPedidos());
            }

            if (usuario.getFavoritos() != null) {
                existingUsuario.getFavoritos().clear();
                existingUsuario.getFavoritos().addAll(usuario.getFavoritos());
            }

            sessionFactory.getCurrentSession().update(existingUsuario);
        } else {
            sessionFactory.getCurrentSession().saveOrUpdate(usuario);
        }
    }

    @Override
    public void eliminarCarritoDeUsuario(Usuario usuario, Carrito carrito) {
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

    @Override
    public void agregarFavorito(Long usuarioId, Producto producto) {
        Usuario usuario = buscarPorId(usuarioId);
        if (usuario != null) {
            usuario.getFavoritos().add(producto);
            sessionFactory.getCurrentSession().saveOrUpdate(usuario);
        }
    }

    @Override
    public void eliminarFavorito(Long usuarioId, Producto producto) {
        Usuario usuario = buscarPorId(usuarioId);
        if (usuario != null) {
            usuario.getFavoritos().removeIf(favorito -> favorito.getIdProducto().equals(producto.getIdProducto()));
            sessionFactory.getCurrentSession().saveOrUpdate(usuario);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Usuario.class, id);
    }
}
