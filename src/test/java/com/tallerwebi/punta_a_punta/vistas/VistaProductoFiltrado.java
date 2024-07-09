package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

import java.util.List;

public class VistaProductoFiltrado extends VistaWeb {

    public VistaProductoFiltrado(Page page) {
        super(page);
    }

    public void navegar() {
        page.navigate("localhost:8080/spring/productoFiltrado");
    }

    public List<String> obtenerNombresDeLosProductos(){ return this.obtenerTextosDeElementos(".product-name"); }

    public String obtenerTextoDelTitulo() { return this.obtenerTextoDelElemento(".tituloProductoBuscado span"); }

    public String obtenerTextoDeError() { return this.obtenerTextoDelElemento(".error"); }

    public void darClickEnElPrimerProducto() { this.darClickEnElPrimerElemento(".compare-button"); }

    public String obtenerNombreDelPrimerProducto() { return this.obtenerTextoDelPrimerElemento(".product-name");  }




}
