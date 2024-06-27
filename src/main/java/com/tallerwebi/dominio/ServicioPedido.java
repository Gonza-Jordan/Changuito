package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPedido {

    void registrar(Pedido pedido) ;

    Pedido buscar(Long id) ;


}
