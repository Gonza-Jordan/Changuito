package com.tallerwebi.dominio;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SupermercadoProductoId implements Serializable {
    private Integer productoId;
    private Integer supermercadoId;

    public SupermercadoProductoId() {
    }
}
