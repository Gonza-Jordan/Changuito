package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Producto;
import org.springframework.stereotype.Repository;


public interface RepositorioProducto {
    Producto buscarProducto(String nombre);
}
