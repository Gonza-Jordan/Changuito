package com.tallerwebi.dominio;

public class Supermercado {
    private double distanciaNumero;
    private String distanciaDescripcion;
    private int banderaId;
    private double lat;
    private double lng;
    private String sucursalNombre;
    private String id;
    private String sucursalTipo;
    private String provincia;
    private String direccion;
    private String banderaDescripcion;
    private String localidad;
    private String comercioRazonSocial;
    private int comercioId;
    private String sucursalId;

    public Supermercado( double distanciaNumero, String distanciaDescripcion,
     int banderaId,    double lat    , double lng    , String sucursalNombre    , String id
    , String sucursalTipo   , String provincia   , String direccion    , String banderaDescripcion
    , String localidad    , String comercioRazonSocial    , int comercioId    , String sucursalId) {
        this.distanciaNumero = distanciaNumero;
        this.distanciaDescripcion = distanciaDescripcion;
        this.banderaId = banderaId;
        this.lat = lat;
        this.lng = lng;
        this.sucursalNombre = sucursalNombre;
        this.id = id;
        this.sucursalTipo = sucursalTipo;
        this.provincia = provincia;
        this.direccion = direccion;
        this.banderaDescripcion = banderaDescripcion;
        this.localidad = localidad;
        this.comercioRazonSocial = comercioRazonSocial;
        this.comercioId = comercioId;
        this.sucursalId = sucursalId;
    }


    public double getDistanciaNumero() {
        return distanciaNumero;
    }

    public void setDistanciaNumero(double distanciaNumero) {
        this.distanciaNumero = distanciaNumero;
    }

    public String getDistanciaDescripcion() {
        return distanciaDescripcion;
    }

    public void setDistanciaDescripcion(String distanciaDescripcion) {
        this.distanciaDescripcion = distanciaDescripcion;
    }

    public int getBanderaId() {
        return banderaId;
    }

    public void setBanderaId(int banderaId) {
        this.banderaId = banderaId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSucursalTipo() {
        return sucursalTipo;
    }

    public void setSucursalTipo(String sucursalTipo) {
        this.sucursalTipo = sucursalTipo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getBanderaDescripcion() {
        return banderaDescripcion;
    }

    public void setBanderaDescripcion(String banderaDescripcion) {
        this.banderaDescripcion = banderaDescripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getComercioRazonSocial() {
        return comercioRazonSocial;
    }

    public void setComercioRazonSocial(String comercioRazonSocial) {
        this.comercioRazonSocial = comercioRazonSocial;
    }

    public int getComercioId() {
        return comercioId;
    }

    public void setComercioId(int comercioId) {
        this.comercioId = comercioId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }
}
