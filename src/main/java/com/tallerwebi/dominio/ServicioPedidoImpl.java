package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPedido")
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {

    private RepositorioPedido repositorioPedido;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido) {
        this.repositorioPedido = repositorioPedido;
    }


    @Override
    public void registrar(Pedido pedido) {
        repositorioPedido.guardar(pedido);
    }

    @Override
    public Pedido buscar(Long id) {
        return repositorioPedido.buscar(id);
             }


}
