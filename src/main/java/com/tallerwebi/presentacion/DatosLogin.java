package com.tallerwebi.presentacion;

public class DatosLogin {
    private String email;
    private String contrasena;

    public DatosLogin() {
    }

    public DatosLogin(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

