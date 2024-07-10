package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {

    void guardar(Usuario usuario);

    Usuario buscar(String email);

    List<Usuario> buscarTodosLosUsuarios();

    void modificar(Usuario usuario);

    void modificarPedidoCarrito(Usuario usuario);

    void eliminarCarritoDeUsuario(Usuario usuario, Carrito carrito);

    void agregarFavorito(Long usuarioId, Producto producto);
    void eliminarFavorito(Long usuarioId, Producto producto);
    Usuario buscarPorId(Long id);
}


