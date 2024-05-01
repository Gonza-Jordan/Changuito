package com.tallerwebi.ChangoMain;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear un supermercado
        Supermercado supermercado = new Supermercado("Mi Supermercado");

        // Agregar productos al supermercado
        supermercado.agregarProducto(new Producto(1, "Leche", 2.5, "123456789"));
        supermercado.agregarProducto(new Producto(2, "Pan", 1.0, "987654321"));

        // Mostrar los productos del supermercado
        List<Producto> productos = supermercado.getProductos();
        System.out.println("Productos en " + supermercado.getNombre() + ":");
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Código de Barras: " + producto.getCodigoBarras());
        }

        // Buscar un producto por su ID
        int idProductoABuscar = 1;
        Producto productoEncontrado = null;
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProductoABuscar) {
                productoEncontrado = producto;
                break;
            }
        }

        if (productoEncontrado != null) {
            System.out.println("\nProducto encontrado:");
            System.out.println("ID: " + productoEncontrado.getIdProducto() + ", Nombre: " + productoEncontrado.getNombre() + ", Precio: " + productoEncontrado.getPrecio() + ", Código de Barras: " + productoEncontrado.getCodigoBarras());
        } else {
            System.out.println("\nProducto no encontrado.");
        }

        // Editar un producto existente
        Producto productoEditado = new Producto(2, "Pan Integral", 1.2, "987654321");
        supermercado.eliminarProducto(2); // Eliminar el producto existente
        supermercado.agregarProducto(productoEditado); // Agregar el producto editado

        // Mostrar los productos del supermercado después de la edición
        System.out.println("\nProductos en " + supermercado.getNombre() + " después de la edición:");
        productos = supermercado.getProductos();
        for (Producto producto : productos) {
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Código de Barras: " + producto.getCodigoBarras());
        }

        // Eliminar un producto del supermercado
        int idProductoAEliminar = 1;
        supermercado.eliminarProducto(idProductoAEliminar);

        // Mostrar los productos del supermercado después de la eliminación
        List<Producto> productosDespues = supermercado.getProductos();
        System.out.println("\nProductos en " + supermercado.getNombre() + " después de la eliminación:");
        for (Producto producto : productosDespues) {
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Código de Barras: " + producto.getCodigoBarras());
        }
    }
}