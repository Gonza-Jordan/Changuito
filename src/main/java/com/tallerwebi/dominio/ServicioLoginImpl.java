package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario(String email) {
        return repositorioUsuario.buscar(email);
    }

    @Override
    public Usuario validarContrasena(String email, String contrasena) {
        Usuario usuario = this.consultarUsuario(email);

        if (usuario != null) {
            return Objects.equals(usuario.getContrasena(), contrasena) ? usuario : null;
        } else {
            return null;
        }
    }


    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscar(usuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }


    @Override
    public void modificar(Usuario usuario){
        repositorioUsuario.modificar(usuario);
    };

}
