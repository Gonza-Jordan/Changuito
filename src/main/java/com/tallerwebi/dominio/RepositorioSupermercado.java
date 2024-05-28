package com.tallerwebi.dominio;

public interface RepositorioSupermercado {

    void guardarSupermercado(Supermercado supermercado);

    void guardarProductoConSupermercado(Supermercado supermercado, Producto producto);
}
