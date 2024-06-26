//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.*;
//import net.bytebuddy.implementation.bind.annotation.Super;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
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
//        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
//
//        supermercadoProductoMock.setSupermercado(supermercadoMock);
//        supermercadoProductoMock.setProducto(productoMock);
//        supermercadoProductoMock.setPrecio(2000.00);
//
//        controladorCarritoCompras.agregarAlCarrito(supermercadoProductoMock.getProducto().getNombre(), supermercadoProductoMock.getPrecio().toString(), supermercadoProductoMock.getProducto().getCodigoBarras(),
//                supermercadoProductoMock.getProducto().getCategoria(), supermercadoProductoMock.getProducto().getSubcategoria(), supermercadoProductoMock.getProducto().getUrlImagen());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//
//        List<SupermercadoProducto> carrito = (List<SupermercadoProducto>) modelAndView.getModel().get("carrito");
//        assertThat(carrito.size(), equalTo(1));
//        assertThat(carrito.get(0).getProducto().getNombre(), equalTo(supermercadoProductoMock.getProducto().getNombre()));
//    }
//
//    @Test
//    void deberiaEliminarProductoDelCarrito() {
//        Producto productoMock = new Producto("Coca Cola", "123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
//
//        supermercadoProductoMock.setSupermercado(supermercadoMock);
//        supermercadoProductoMock.setProducto(productoMock);
//        supermercadoProductoMock.setPrecio(2000.00);
//
//        controladorCarritoCompras.agregarAlCarrito(supermercadoProductoMock.getProducto().getNombre(), supermercadoProductoMock.getPrecio().toString(), supermercadoProductoMock.getProducto().getCodigoBarras(),
//                supermercadoProductoMock.getProducto().getCategoria(), supermercadoProductoMock.getProducto().getSubcategoria(), supermercadoProductoMock.getProducto().getUrlImagen());
//
//        controladorCarritoCompras.eliminarDelCarrito(supermercadoProductoMock.getProducto().getCodigoBarras());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//
//        List<SupermercadoProducto> carrito = (List<SupermercadoProducto>) modelAndView.getModel().get("carrito");
//
//        assertThat(carrito.isEmpty(), equalTo(true));
//    }
//
//    @Test
//    void deberiaMostrarCarritoDeCompras() {
//        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//
//        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
//        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();
//
//        supermercadoProductoMock.setSupermercado(supermercadoMock);
//        supermercadoProductoMock.setProducto(productoMock);
//        supermercadoProductoMock.setPrecio(900.00);
//
//        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
//        otroSupermercadoProductoMock.setProducto(otroProductoMock);
//        otroSupermercadoProductoMock.setPrecio(800.00);
//
//
//        controladorCarritoCompras.agregarAlCarrito(supermercadoProductoMock.getProducto().getNombre(), supermercadoProductoMock.getPrecio().toString(), supermercadoProductoMock.getProducto().getCodigoBarras(),
//                supermercadoProductoMock.getProducto().getCategoria(), supermercadoProductoMock.getProducto().getSubcategoria(), supermercadoProductoMock.getProducto().getUrlImagen());
//
//        controladorCarritoCompras.agregarAlCarrito(otroSupermercadoProductoMock.getProducto().getNombre(), otroSupermercadoProductoMock.getPrecio().toString(), otroSupermercadoProductoMock.getProducto().getCodigoBarras(),
//                otroSupermercadoProductoMock.getProducto().getCategoria(), otroSupermercadoProductoMock.getProducto().getSubcategoria(), otroSupermercadoProductoMock.getProducto().getUrlImagen());
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//
//        List<SupermercadoProducto> carrito = (List<SupermercadoProducto>) modelAndView.getModel().get("carrito");
//
//        assertThat(modelAndView.getViewName(), equalTo("carritoCompras"));
//        assertThat(carrito.size(), equalTo(2));
//        assertThat(modelAndView.getModel().get("cantidadProductos"), equalTo(2));
//    }
//
//    @Test
//    void deberiaVaciarCarritoDeCompras() {
//        Producto productoMock = new Producto("Coca Cola","123456789", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Producto otroProductoMock = new Producto("Sprite","123123123", Categoria.Bebidas, Subcategoria.Gaseosas, "");
//        Supermercado supermercadoMock = new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://example.com/logo_carrefour.png");
//
//        SupermercadoProducto supermercadoProductoMock = new SupermercadoProducto();
//        SupermercadoProducto otroSupermercadoProductoMock = new SupermercadoProducto();
//
//        supermercadoProductoMock.setSupermercado(supermercadoMock);
//        supermercadoProductoMock.setProducto(productoMock);
//        supermercadoProductoMock.setPrecio(900.00);
//
//        otroSupermercadoProductoMock.setSupermercado(supermercadoMock);
//        otroSupermercadoProductoMock.setProducto(otroProductoMock);
//        otroSupermercadoProductoMock.setPrecio(800.00);
//
//
//        controladorCarritoCompras.agregarAlCarrito(supermercadoProductoMock.getProducto().getNombre(), supermercadoProductoMock.getPrecio().toString(), supermercadoProductoMock.getProducto().getCodigoBarras(),
//                supermercadoProductoMock.getProducto().getCategoria(), supermercadoProductoMock.getProducto().getSubcategoria(), supermercadoProductoMock.getProducto().getUrlImagen());
//
//        controladorCarritoCompras.agregarAlCarrito(otroSupermercadoProductoMock.getProducto().getNombre(), otroSupermercadoProductoMock.getPrecio().toString(), otroSupermercadoProductoMock.getProducto().getCodigoBarras(),
//                otroSupermercadoProductoMock.getProducto().getCategoria(), otroSupermercadoProductoMock.getProducto().getSubcategoria(), otroSupermercadoProductoMock.getProducto().getUrlImagen());
//
//
//        controladorCarritoCompras.vaciarCarrito();
//
//        ModelAndView modelAndView = controladorCarritoCompras.verCarrito();
//        List<SupermercadoProducto> carrito = (List<SupermercadoProducto>) modelAndView.getModel().get("carrito");
//
//        assertThat(carrito.isEmpty(), equalTo(true));
//    }
//}
