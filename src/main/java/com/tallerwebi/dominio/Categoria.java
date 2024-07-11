package com.tallerwebi.dominio;

public enum Categoria {

    Almacen(Subcategoria.Arroz, Subcategoria.Aceite, Subcategoria.Harina, Subcategoria.Pastas, Subcategoria.Endulzantes),
    Perfumeria(Subcategoria.Acondicionador, Subcategoria.Shampoo, Subcategoria.Dentifricos),
    Bebidas(Subcategoria.Agua, Subcategoria.Gaseosas, Subcategoria.Jugos, Subcategoria.Vinos, Subcategoria.Cervezas),
    Verduleria(Subcategoria.Tomate, Subcategoria.Manzana, Subcategoria.Lechuga, Subcategoria.Zanahoria, Subcategoria.Papa),
    Limpieza(Subcategoria.Guantes, Subcategoria.Lavandina, Subcategoria.Detergente, Subcategoria.Esponjas, Subcategoria.Trapos),
    Lacteos(Subcategoria.Leche, Subcategoria.Manteca, Subcategoria.Quesos, Subcategoria.Yogures, Subcategoria.Crema);

    private final Subcategoria[] subcategorias;

    Categoria(Subcategoria... subcategorias) {
        this.subcategorias = subcategorias;
    }

    public Subcategoria[] getSubcategorias() {
        return subcategorias;
    }


}
