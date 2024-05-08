package com.tallerwebi.dominio;

public enum Categoria {

    Almacen(Subcategoria.Arroz, Subcategoria.Aceite, Subcategoria.Harina, Subcategoria.Pastas),
    Perfumeria(Subcategoria.Toallitas, Subcategoria.Acondicionador, Subcategoria.Shampoo, Subcategoria.Dentifricos),
    Bebidas(Subcategoria.Agua, Subcategoria.Gaseosas, Subcategoria.Jugos, Subcategoria.Vinos),
    Verduleria(Subcategoria.Tomate, Subcategoria.Manzana, Subcategoria.Lechuga, Subcategoria.Zanahoria),
    Limpieza(Subcategoria.Guantes, Subcategoria.Servilletas, Subcategoria.Escobas, Subcategoria.Lavandina),
    Lacteos(Subcategoria.Leche, Subcategoria.Manteca, Subcategoria.Quesos, Subcategoria.Yogures);

    private final Subcategoria[] subcategorias;

    Categoria(Subcategoria... subcategorias) {
        this.subcategorias = subcategorias;
    }

    public Subcategoria[] getSubcategorias() {
        return subcategorias;
    }


}
