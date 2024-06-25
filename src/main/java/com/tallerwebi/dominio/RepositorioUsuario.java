package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {

    void guardar(Usuario usuario);

    Usuario buscar(String email);

    void modificar(Usuario usuario);

    void eliminarCarritoDeUsuario(Usuario usuario, Carrito carrito);

}


