public class Main {
    public static void main(String[] args) {
        // Creo una lista de productos
        GestorProductos gestor = new GestorProductos();

        // Agrego productos
        gestor.agregarProducto(new Producto(1, "Arroz", 2.5, "123456789"));
        gestor.agregarProducto(new Producto(2, "Pan", 1.0, "987654321"));

        // Listo un producto agregado
        System.out.println("Productos agregados:");
        for (Producto producto : gestor.getListaProductos()) {
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Código de Barras: " + producto.getCodigoBarras());
        }

        //Edito un producto
        Producto nuevoProducto = new Producto(2, "Pan Integral", 1.2, "987654321");
        gestor.editarProducto(2, nuevoProducto);

        // Mostrar los productos después de la edición
        System.out.println("Productos después de la edición:");
        for (Producto producto : gestor.getListaProductos()) {
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Código de Barras: " + producto.getCodigoBarras());
        }
    }
}