package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private RepositorioCarrito repositorioCarrito;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito) {
        this.repositorioCarrito = repositorioCarrito;
    }

    @Override
    public Carrito consultarCarrito(Date stamp) {
        return repositorioCarrito.buscar(stamp);
    }

    @Override
    public Carrito consultarCarritoPorId(Long id) {
        return repositorioCarrito.buscarPorId(id);
    }
//
//    @Override
//    public Usuario validarContrasena(String email, String contrasena) {
//        Usuario usuario = this.consultarUsuario(email);
//
//        if (usuario != null) {
//            return Objects.equals(usuario.getContrasena(), contrasena) ? usuario : null;
//        } else {
//            return null;
//        }
//    }


    @Override
    public void registrar(Carrito carrito) {
//        Usuario usuarioEncontrado = repositorioUsuario.buscar(usuario.getEmail());
//        if (usuarioEncontrado != null) {
//            throw new UsuarioExistente();
//        }
        repositorioCarrito.guardar(carrito);
    }


    @Override
    public void modificar(Carrito carrito) {
        repositorioCarrito.modificar(carrito);
    }

    ;

}
