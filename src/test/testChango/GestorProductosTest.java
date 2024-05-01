import org.junit.Test;
import static org.junit.Assert.*;

public class GestorProductosTest {

    @Test
    public void testBuscarProductoExistente() {
        GestorProductos gestor = new GestorProductos();
        Producto producto1 = new Producto(1, "Arroz", 2.5, "123456789");
        Producto producto2 = new Producto(2, "Pan", 1.0, "987654321");
        gestor.agregarProducto(producto1);
        gestor.agregarProducto(producto2);

        Producto productoEncontrado = gestor.buscarProducto(1);
        assertNotNull(productoEncontrado);
        assertEquals(producto1, productoEncontrado);
    }

    @Test
    public void testBuscarProductoNoExistente() {
        GestorProductos gestor = new GestorProductos();
        Producto producto1 = new Producto(1, "Arroz", 2.5, "123456789");
        Producto producto2 = new Producto(2, "Pan", 1.0, "987654321");
        gestor.agregarProducto(producto1);
        gestor.agregarProducto(producto2);

        Producto productoEncontrado = gestor.buscarProducto(3);
        assertNull(productoEncontrado);
    }

    @Test
    public void testAgregarProducto() {
        GestorProductos gestor = new GestorProductos();
        Producto producto = new Producto(1, "Arroz", 2.5, "123456789");
        gestor.agregarProducto(producto);
        assertTrue(gestor.getListaProductos().contains(producto));
    }

    @Test
    public void testEditarProducto() {
        GestorProductos gestor = new GestorProductos();
        Producto productoOriginal = new Producto(1, "Arroz", 2.5, "123456789");
        Producto productoEditado = new Producto(1, "Arroz fino", 2.3, "123456789");
        gestor.agregarProducto(productoOriginal);
        gestor.editarProducto(1, productoEditado);
        assertTrue(gestor.getListaProductos().contains(productoEditado));
        assertFalse(gestor.getListaProductos().contains(productoOriginal));
    }

    @Test
    public void testEliminarProducto() {
        GestorProductos gestor = new GestorProductos();
        Producto producto1 = new Producto(1, "Leche", 2.5, "123456789");
        Producto producto2 = new Producto(2, "Pan", 1.0, "987654321");
        gestor.agregarProducto(producto1);
        gestor.agregarProducto(producto2);

        gestor.eliminarProducto(1);
        assertFalse(gestor.getListaProductos().contains(producto1));
        assertTrue(gestor.getListaProductos().contains(producto2));

        gestor.eliminarProducto(2);
        assertFalse(gestor.getListaProductos().contains(producto2));
    }
}
