package com.tallerwebi.dominio;
import com.tallerwebi.dominio.Subcategoria;

public enum Categoria {

//    Almacen, Perfumeria, Bebidas, Verduleria, Limpieza, Lacteos;

    Almacen(Subcategoria.Arroz, Subcategoria.Aceite, Subcategoria.Harina, Subcategoria.Pasta),
    Perfumeria(Subcategoria.Toallitas, Subcategoria.Acondicionador, Subcategoria.Shampoo, Subcategoria.Dentifricos),
    Bebidas(Subcategoria.Agua, Subcategoria.Gaseosa, Subcategoria.Jugos, Subcategoria.Vino),
    Verduleria(),
    Limpieza(),
    Lacteos();

    private final Subcategoria[] subcategorias;

    Categoria(Subcategoria... subcategorias) {
        this.subcategorias = subcategorias;
    }

    public Subcategoria[] getSubcategorias() {
        return subcategorias;
    }


}
