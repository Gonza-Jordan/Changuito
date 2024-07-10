package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

import java.util.List;

public interface ServicioUsuario {

    Usuario consultarUsuario(String email);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario validarContrasena(String email, String contrasena);
    void modificar(Usuario usuario);
    void modificarPedidoCarrito(Usuario usuario);
    List<Carrito> eliminarCarritoDeUsuario(Usuario usuario, Carrito carrito);

    void agregarAFavoritos(Usuario usuario, List<Producto> productos);

    void eliminarDeFavoritos(Usuario usuario, Producto producto);
}
