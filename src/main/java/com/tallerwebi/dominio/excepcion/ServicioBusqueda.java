package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Producto;

public interface ServicioBusqueda {
    Producto consultarProducto(String nombre);
}
