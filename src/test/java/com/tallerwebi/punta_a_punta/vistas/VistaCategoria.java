package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

import java.util.List;

public class VistaCategoria extends VistaWeb {

    public VistaCategoria(Page page) {
        super(page);
    }

    public void navegar() {
        page.navigate("localhost:8080/spring/categoria");
    }

    public List<String> obtenerNombresDelCarrusel() { return this.obtenerTextosDeElementos(".carousel-item");  }

    public void darClickEnCategoriaAceite() { this.darClickEnUnoDeMuchosElementos(".carousel-item", "Aceite");  }
}
