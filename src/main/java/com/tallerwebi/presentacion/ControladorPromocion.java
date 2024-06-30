package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ControladorPromocion {

    private ServicioPromocion servicioPromocion;

    @Autowired
    public ControladorPromocion(ServicioPromocion servicioPromocion) {
        this.servicioPromocion = servicioPromocion;
    }

//    @RequestMapping(path = "/promociones", method = RequestMethod.GET)
//    public ModelAndView irAPromociones() {
//
//        ModelMap model = new ModelMap();
//
//        List<Promocion> promociones = servicioPromocion.buscarPromociones();
//
//        if (promociones != null){
//            model.put("promociones", promociones);
//        }else {
//            model.put("error", "No hay promociones");
//        }
//
//        return new ModelAndView("promociones", model);
//    }

    @RequestMapping(path = "/promociones", method = RequestMethod.GET)
    public ModelAndView irAPromociones() {
        ModelMap model = new ModelMap();

        List<Promocion> promociones = servicioPromocion.buscarPromociones();

        if (promociones != null) {
           for (Promocion promocion : promociones) {
                if (promocion instanceof Paquete) {
                    Paquete paquete = (Paquete) promocion;
                    paquete.setPrecioFinal(formatearPrecio(paquete.getPrecioFinal()));
                }
            }

            model.put("promociones", promociones);
        } else {
            model.put("error", "No hay promociones");
        }

        return new ModelAndView("promociones", model);
    }

    private Double formatearPrecio(Double precio) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String precioFormateado = df.format(precio);
        precioFormateado = precioFormateado.replace(",", ".");
        return Double.parseDouble(precioFormateado);
    }
}



