package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private RepositorioCarrito repositorioCarrito;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito) {
        this.repositorioCarrito = repositorioCarrito;
    }

    @Override
    public Carrito consultarCarrito(Date stamp) {
        return repositorioCarrito.buscar(stamp);
    }

    @Override
    public Carrito consultarCarritoPorId(Long id) {
        return repositorioCarrito.buscarPorId(id);
    }


    @Override
    public void registrar(Carrito carrito) {
//        Usuario usuarioEncontrado = repositorioUsuario.buscar(usuario.getEmail());
//        if (usuarioEncontrado != null) {
//            throw new UsuarioExistente();
//        }
        repositorioCarrito.guardar(carrito);
    }


    @Override
    public void modificar(Carrito carrito) {
        repositorioCarrito.modificar(carrito);
    }

    @Override
    public void eliminar(Carrito carrito) {
        repositorioCarrito.eliminar(carrito);
    }

    @Override
    public void agregarPromocionAlCarrito(Long carritoId, Promocion promocion) {
        Carrito carrito = repositorioCarrito.buscarPorId(carritoId);
        boolean existe = carrito.getPromocion().stream()
                .anyMatch(p -> p.getIdPromocion().equals(promocion.getIdPromocion()));
        if (!existe) {
            carrito.getPromocion().add(promocion);
            repositorioCarrito.modificar(carrito);
        } else {
            throw new RuntimeException("La promoción ya existe en el carrito");
        }
    }

}
