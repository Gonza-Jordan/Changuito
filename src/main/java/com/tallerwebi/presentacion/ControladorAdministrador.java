package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


@Controller
public class ControladorAdministrador {

    private ServicioAdministrador servicioAdministrador;

    @Autowired
    public ControladorAdministrador(ServicioAdministrador servicioAdministrador) {
        this.servicioAdministrador = servicioAdministrador;
    }

    @RequestMapping(path = "/administrador", method = RequestMethod.GET)
    public ModelAndView irAdministrador(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Supermercado supermercado = usuario.getSupermercado();

        ModelMap model = new ModelMap();

        model.put("supermercado", supermercado);

        return new ModelAndView("administrador", model);
    }


    @RequestMapping(path = "/crearCombo", method = RequestMethod.GET)
    public ModelAndView irACombo(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();
        Supermercado supermercado = usuario.getSupermercado();

        ModelMap model = new ModelMap();

        model.put("supermercado", supermercado);

        List<SupermercadoProducto> supermercadoProductoList = this.servicioAdministrador.buscarProductosDeUnSupermercado(idSupermercado);

        if (supermercadoProductoList != null) {
            supermercadoProductoList.sort(Comparator.comparing(sp -> sp.getProducto().getNombre()));
            model.put("productos", supermercadoProductoList);
        } else {
            model.put("error", "No hay productos en el supermercado");
        }

        return new ModelAndView("crearCombo", model);
    }

    @RequestMapping(path = "/guardarCombo", method = RequestMethod.POST)
    public String crearCombo(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("cantidadVendida") Integer cantidadVendida,
            @RequestParam("cantidadCobrada") Integer cantidadCobrada,
            @RequestParam(value = "productoId", required = false) Integer productoId,
            Model model,
            RedirectAttributes redirectAttrs, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();

        try {
            SupermercadoProducto supermercadoProducto = this.servicioAdministrador.buscarSupermercadoProducto(productoId, idSupermercado);
            Combo combo = this.servicioAdministrador.crearCombo(supermercadoProducto, fechaInicio, fechaFin, cantidadVendida, cantidadCobrada);
            servicioAdministrador.guardarCombo(combo);
        } catch (SinIdProductoException e) {
            redirectAttrs.addFlashAttribute("error", "No se seleccionó ningún producto");
            return "redirect:/crearCombo";
        } catch (CantidadVendidaNoPuedeSerMenorOIgualALaCobradaException e) {
            redirectAttrs.addFlashAttribute("error", "La cantidad vendida no puede ser menor o igual a la cantidad cobrada");
            return "redirect:/crearCombo";
        } catch (CantidadInvalidaException e) {
            redirectAttrs.addFlashAttribute("error", "Cantidad inválida");
            return "redirect:/crearCombo";
        } catch (FechaInvalidaException e) {
            redirectAttrs.addFlashAttribute("error", "La fecha de fin no puede ser anterior o igual a la fecha de inicio");
            return "redirect:/crearCombo";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Ha ocurrido un error al crear el combo");
            return "redirect:/crearPaquete";
        }

        redirectAttrs.addFlashAttribute("exito", "Combo creado exitosamente");
        return "redirect:/crearCombo";
    }

    @RequestMapping(path = "/crearPaquete", method = RequestMethod.GET)
    public ModelAndView irAPaquete(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();
        Supermercado supermercado = usuario.getSupermercado();

        ModelMap model = new ModelMap();

        model.put("supermercado", supermercado);

        List<SupermercadoProducto> supermercadoProductoList = this.servicioAdministrador.buscarProductosDeUnSupermercado(idSupermercado);

        if (supermercadoProductoList != null) {
            supermercadoProductoList.sort(Comparator.comparing(sp -> sp.getProducto().getNombre()));
            model.put("productos", supermercadoProductoList);
        } else {
            model.put("error", "No hay productos en el supermercado");
        }

        return new ModelAndView("crearPaquete", model);
    }

    @RequestMapping(path = "/guardarPaquete", method = RequestMethod.POST)
    public String crearPaquete(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("descuento") Double descuento,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "productoIds", required = false) List<Integer> productoIds,
            Model model,
            RedirectAttributes redirectAttrs, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();

        try {
            List<SupermercadoProducto> productos = this.servicioAdministrador.buscarProductosPorIds(productoIds, idSupermercado);
            Paquete paquete = this.servicioAdministrador.crearPaquete(productos, fechaInicio, fechaFin, descuento, nombre);
            servicioAdministrador.guardarPaquete(paquete);
        } catch (SinIdProductoException e) {
            redirectAttrs.addFlashAttribute("error", "No se seleccionó ningún producto");
            return "redirect:/crearPaquete";
        } catch (FechaInvalidaException e) {
            redirectAttrs.addFlashAttribute("error", "La fecha de fin no puede ser anterior o igual a la fecha de inicio");
            return "redirect:/crearPaquete";
        } catch (DescuentoInvalidoException e) {
            redirectAttrs.addFlashAttribute("error", "Descuento inválido");
            return "redirect:/crearPaquete";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Ha ocurrido un error al crear el paquete");
            return "redirect:/crearPaquete";
        }

        redirectAttrs.addFlashAttribute("exito", "Paquete creado exitosamente");
        return "redirect:/crearPaquete";
    }

    @RequestMapping(path = "/asignarPrecioYDescuento", method = RequestMethod.GET)
    public ModelAndView irAAsignarPrecioYDescuento(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();
        Supermercado supermercado = usuario.getSupermercado();

        ModelMap model = new ModelMap();

        model.put("supermercado", supermercado);

        List<SupermercadoProducto> supermercadoProductoList = this.servicioAdministrador.buscarProductosDeUnSupermercado(idSupermercado);

        if (supermercadoProductoList != null) {
            supermercadoProductoList.sort(Comparator.comparing(sp -> sp.getProducto().getNombre()));
            model.put("productos", supermercadoProductoList);
        } else {
            model.put("error", "No hay productos en el supermercado");
        }

        return new ModelAndView("asignarPrecioYDescuento", model);
    }

    @RequestMapping(path = "/guardarPrecioYDescuento", method = RequestMethod.POST)
    public String guardarPrecioYDescuento(
            @RequestParam(value = "precio", required = false) Double precio,
            @RequestParam(value = "descuento", required = false) Double descuento,
            @RequestParam(value = "productoId", required = false) Integer productoId,
            Model model,
            RedirectAttributes redirectAttrs, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idSupermercado = usuario.getSupermercado().getIdSupermercado();

        try {
            SupermercadoProducto supermercadoProducto = this.servicioAdministrador.buscarSupermercadoProducto(productoId, idSupermercado);
            this.servicioAdministrador.actualizarPrecioYDescuento(supermercadoProducto, precio, descuento);

        } catch (SinIdProductoException e) {
            redirectAttrs.addFlashAttribute("error", "No se seleccionó ningún producto");
            return "redirect:/asignarPrecioYDescuento";
        } catch (PrecioInvalidoException e) {
            redirectAttrs.addFlashAttribute("error", "El precio no puede ser menor a 0");
            return "redirect:/asignarPrecioYDescuento";
        } catch (DescuentoInvalidoException e) {
            redirectAttrs.addFlashAttribute("error", "Descuento inválido");
            return "redirect:/asignarPrecioYDescuento";
        }catch (SinPrecioNiDescuentoException e) {
            redirectAttrs.addFlashAttribute("error", "No se asignó precio ni descuento");
            return "redirect:/asignarPrecioYDescuento";
        }
        redirectAttrs.addFlashAttribute("exito", "Producto actualizado exitosamente");
        return "redirect:/asignarPrecioYDescuento";
    }

}



