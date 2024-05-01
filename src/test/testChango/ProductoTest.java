import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {
    @Test
    public void testGetId() {
        Producto producto = new Producto(1, "Arroz", 2.5, "123456789");
        assertEquals(1, producto.getIdProducto());
    }

    @Test
    public void testGetNombre() {
        Producto producto = new Producto(1,"Arroz", 2.5, "123456789");
        assertEquals("Arroz", producto.getNombre());
    }

    @Test
    public void testGetPrecio() {
        Producto producto = new Producto(1,"Arroz", 2.5, "123456789");
        assertEquals(2.5, producto.getPrecio(), 0.001); // El tercer parámetro es la tolerancia para comparaciones de punto flotante
    }

    @Test
    public void testGetCodigoBarras() {
        Producto producto = new Producto(1,"Arroz", 2.5, "123456789");
        assertEquals("123456789", producto.getCodigoBarras());
    }
}