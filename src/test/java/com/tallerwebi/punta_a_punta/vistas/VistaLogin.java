package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaLogin extends VistaWeb {

    public VistaLogin(Page page) {
        super(page);
    }

    public void navegar() {
        page.navigate("localhost:8080/spring/login");
    }

    public String obtenerMensajeDeError(){
        return this.obtenerTextoDelElemento(".alert");
    }

    public void escribirEMAIL(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#contrasena", clave);
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento("#btn-login");
    }
}
