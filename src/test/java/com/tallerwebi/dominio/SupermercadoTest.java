//package com.tallerwebi.dominio;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class SupermercadoTest {
//
//    @Test
//    public void testAgregarProductoSupermercado() {
//        Supermercado supermercado = new Supermercado("Mi Supermercado");
//        Producto producto = new Producto(1, "Arroz", 2.5, "123456789", Categoria.Almacen, Subcategoria.Arroz);
//        supermercado.agregarProducto(producto);
//        assertTrue(supermercado.getProductos().contains(producto));
//    }
//
//    @Test
//    public void testEliminarProductoSupermercado() {
//        Supermercado supermercado = new Supermercado("Mi Supermercado");
//        Producto producto = new Producto(1, "Arroz", 2.5, "123456789", Categoria.Almacen, Subcategoria.Arroz);
//        supermercado.agregarProducto(producto);
//        supermercado.eliminarProducto(1);
//        assertFalse(supermercado.getProductos().contains(producto));
//    }
//
//    @Test
//    public void testBuscarProductoExistenteSupermercado() {
//        Supermercado supermercado = new Supermercado("Mi Supermercado");
//        Producto producto = new Producto(1, "Arroz", 2.5, "123456789", Categoria.Almacen, Subcategoria.Arroz);
//        supermercado.agregarProducto(producto);
//        Producto productoEncontrado = supermercado.buscarProducto(1);
//        assertNotNull(productoEncontrado);
//        assertEquals(producto, productoEncontrado);
//    }
//
//    @Test
//    public void testBuscarProductoNoExistenteSupermercado() {
//        Supermercado supermercado = new Supermercado("Mi Supermercado");
//        Producto producto = new Producto(1, "Arroz", 2.5, "123456789", Categoria.Almacen, Subcategoria.Arroz);
//        supermercado.agregarProducto(producto);
//        Producto productoEncontrado = supermercado.buscarProducto(2);
//        assertNull(productoEncontrado);
//    }
//
//    @Test
//    public void testSupermercado() {
//        Supermercado supermercado = new Supermercado("Mi Supermercado");
//        assertNotNull(supermercado);
//        assertEquals("Mi Supermercado", supermercado.getNombre());
//        assertTrue(supermercado.getProductos().isEmpty());
//    }
//}