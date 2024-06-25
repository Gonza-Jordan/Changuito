package com.tallerwebi.dominio;

import javax.persistence.Embeddable;
import java.io.Serializable;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class SupermercadoProductoId implements Serializable {
    private Integer productoId;
    private Integer supermercadoId;

    public SupermercadoProductoId() {
    }

    public SupermercadoProductoId(Integer productoId, Integer supermercadoId) {
        this.productoId = productoId;
        this.supermercadoId = supermercadoId;
    }

    // Getters y Setters
    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getSupermercadoId() {
        return supermercadoId;
    }

    public void setSupermercadoId(Integer supermercadoId) {
        this.supermercadoId = supermercadoId;
    }

    // Sobrescribir equals() y hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupermercadoProductoId that = (SupermercadoProductoId) o;

        if (!productoId.equals(that.productoId)) return false;
        return supermercadoId.equals(that.supermercadoId);
    }

    @Override
    public int hashCode() {
        int result = productoId.hashCode();
        result = 31 * result + supermercadoId.hashCode();
        return result;
    }
}

