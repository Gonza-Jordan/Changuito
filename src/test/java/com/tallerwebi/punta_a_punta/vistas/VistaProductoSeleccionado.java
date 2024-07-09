package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

import java.util.List;

public class VistaProductoSeleccionado extends VistaWeb {

    public VistaProductoSeleccionado(Page page) {
        super(page);
    }

    public void navegar() {
        page.navigate("localhost:8080/spring/producto_seleccionado");
    }

    public String obtenerNombreDelProducto() { return this.obtenerTextoDelElemento(".product-title-producto-seleccionado"); }

    public List<String> obtenerNombresDeLosProductos() { return this.obtenerTextosDeElementos(".nombre-producto-card");  }
}
