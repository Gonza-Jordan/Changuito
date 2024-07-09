package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

import java.util.List;

public class VistaHome extends VistaWeb {

    public VistaHome(Page page) {
        super(page);
    }

    public void navegar() {
        page.navigate("localhost:8080/spring/home");
    }

    public String obtenerTextoDeLaBarraDeNavegacion(){
        return this.obtenerTextoDelElemento(".navbar-brand");
    }

    public String obtenerTextoDelBotonDelBuscador(){
        return this.obtenerTextoDelElemento(".boton-buscar");
    }

    public void escribirPalabraABuscar(String palabra){
        this.escribirEnElElemento(".form-buscador input", palabra);
    }

    public void darClickEnBuscar(){
        this.darClickEnElElemento(".boton-buscar");
    }

    public String obtenerTextoDelHeader() { return this.obtenerTextoDelElemento("#miCuenta"); }

    public void darClickEnMiCuenta() {  this.darClickEnElElemento("#miCuenta"); }

    public List<String> obtenerNombresDelCarrusel() { return this.obtenerTextosDeElementos(".carousel-item");  }

    public void darClickEnCategoriaAlmacen() { this.darClickEnUnoDeMuchosElementos(".carousel-item", "Almacen");  }
}
