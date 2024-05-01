import java.util.ArrayList;
import java.util.List;

public class GestorProductos {
    private List<Producto> listaProductos;

    public GestorProductos() {
        this.listaProductos = new ArrayList<>();
    }

    //Agrego producto, paso la lista.
    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    //Retorno la lista de los productos.
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    //Edito un producto
    public void editarProducto(int id, Producto nuevoProducto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getIdProducto() == id) {
                listaProductos.set(i, nuevoProducto);
                return;
            }
        }
        System.out.println("No se encontró un producto con el ID especificado.");
    }

    //Eliminar un producto
    public void eliminarProducto(int idProducto) {
        listaProductos.removeIf(producto -> producto.getIdProducto() == idProducto);
    }

    //Busco un producto
    public Producto buscarProducto(int idProducto) {
        for (Producto producto : listaProductos) {
            if (producto.getIdProducto() == idProducto) {
                return producto;
            }
        }
        return null;
    }
}