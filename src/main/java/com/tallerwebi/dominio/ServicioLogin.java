package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario validarContrasena(String contrasena, String email);

}
