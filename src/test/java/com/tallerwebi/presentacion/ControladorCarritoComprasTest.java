//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.Categoria;
//import com.tallerwebi.dominio.Producto;
//import com.tallerwebi.dominio.Subcategoria;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//
//public class ControladorCarritoComprasTest {
//
//    private ControladorCarritoCompras controladorCarritoCompras;
//
//    @BeforeEach
//    public void init() {
//        this.controladorCarritoCompras = new ControladorCarritoCompras();
//    }
//
//    @Test
//    void deberiaAgregarProductoAlCarrito() {
//        Producto producto = new Producto("Producto 1", "123456", Categoria.Lacteos, Subcategoria.Leche, "");
//
//        controladorCarritoCompras.agregarAlCarrito(producto.getNombre(), producto.getIdProducto(), producto.getCodigoBarras(),
//                producto.getCategoria(), producto.getSubcategoria(), producto.getUrlImagen());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//
//        List<Producto> carrito = (List<Producto>) modelAndView.getModel().get("carrito");
//        assertThat(carrito.size(), equalTo(1));
//        assertThat(carrito.get(0).getNombre(), equalTo("Producto 1"));
//    }
//
//    @Test
//    void deberiaEliminarProductoDelCarrito() {
//        Producto producto = new Producto("Producto 1", "123456", Categoria.Lacteos, Subcategoria.Leche, "");
//        controladorCarritoCompras.agregarAlCarrito(producto.getNombre(), producto.getIdProducto(), producto.getCodigoBarras(),
//                producto.getCategoria(), producto.getSubcategoria(), producto.getUrlImagen());
//
//        controladorCarritoCompras.eliminarDelCarrito(producto.getCodigoBarras());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//        List<Producto> carrito = (List<Producto>) modelAndView.getModel().get("carrito");
//
//        assertThat(carrito.isEmpty(), equalTo(true));
//    }
//
//    @Test
//    void deberiaMostrarCarritoDeCompras() {
//        Producto producto1 = new Producto("Producto 1", "123456", Categoria.Lacteos, Subcategoria.Leche, "");
//        Producto producto2 = new Producto("Producto 2", "654321", Categoria.Bebidas, Subcategoria.Jugos, "");
//        controladorCarritoCompras.agregarAlCarrito(producto1.getNombre(), producto1.getIdProducto(), producto1.getCodigoBarras(),
//                producto1.getCategoria(), producto1.getSubcategoria(), producto1.getUrlImagen());
//        controladorCarritoCompras.agregarAlCarrito(producto2.getNombre(), producto2.getIdProducto(), producto2.getCodigoBarras(),
//                producto2.getCategoria(), producto2.getSubcategoria(), producto2.getUrlImagen());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//
//        List<Producto> carrito = (List<Producto>) modelAndView.getModel().get("carrito");
//
//        assertThat(modelAndView.getViewName(), equalTo("carritoCompras"));
//        assertThat(carrito.size(), equalTo(2));
//        assertThat(modelAndView.getModel().get("cantidadProductos"), equalTo(2));
//    }
//
//    @Test
//    void deberiaVaciarCarritoDeCompras() {
//        Producto producto1 = new Producto("Producto 1", "123456", Categoria.Lacteos, Subcategoria.Leche, "");
//        Producto producto2 = new Producto("Producto 2", "654321", Categoria.Bebidas, Subcategoria.Jugos, "");
//        controladorCarritoCompras.agregarAlCarrito(producto1.getNombre(), producto1.getIdProducto(), producto1.getCodigoBarras(),
//                producto1.getCategoria(), producto1.getSubcategoria(), producto1.getUrlImagen());
//        controladorCarritoCompras.agregarAlCarrito(producto2.getNombre(), producto2.getIdProducto(), producto2.getCodigoBarras(),
//                producto2.getCategoria(), producto2.getSubcategoria(), producto2.getUrlImagen());
//
//        controladorCarritoCompras.vaciarCarrito();
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//        List<Producto> carrito = (List<Producto>) modelAndView.getModel().get("carrito");
//
//        assertThat(carrito.isEmpty(), equalTo(true));
//    }
//}
