package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;

public interface RepositorioPedido {

    void guardar(Pedido pedido);

    Pedido buscar(Long id);

}


