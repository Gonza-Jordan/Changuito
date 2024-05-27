package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Supermercado;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorHome {

    public ControladorHome() {
    }

    @GetMapping("/home")
    public ModelAndView irAHome() {
        ModelAndView modelAndView = new ModelAndView("home");

        // Crear una lista simulada de supermercados
        List<Supermercado> supermercados = Arrays.asList(
                new Supermercado("Carrefour", "Avenida Mosconi 2871", "San Justo", "https://carrefourar.vtexassets.com/assets/vtex/assets-builder/carrefourar.theme/74.0.0/logo/logo___8ebc4231614a7b41a4258354ce76e1e1.svg"),
                new Supermercado("Coto", "Avenida Brigadier Juan Manuel de Rosas 3990", "San Justo", "https://logowik.com/content/uploads/images/supermercado-coto4935.logowik.com.webp"),
                new Supermercado("Jumbo", "Boulevard Buenos Aires 1001", "Lomas del Mirador", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQByoCb8u-rE8EFcTlcQbvHtcoa8HR-_d6_deVSuuF06w&s"),
                new Supermercado("Dia", "Avenida Juan Manuel de Rosas 11000", "González Catán", "https://exportargentina.org.ar/companyimages/15441142122096.jpg"),
                new Supermercado("Chango Mas", "Ruta 3 Km 29", "Isidro Casanova", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9wcqaCozuqN3MpF3B0hPbLaRxBPXfJTX97HlZQ8hRaA&s")
        );

        // Agregar los supermercados al modelo
        modelAndView.addObject("supermercados", supermercados);

        // Obtener y agregar las categorías y los iconos
        List<Categoria> categorias = Arrays.asList(Categoria.values());
        modelAndView.addObject("categorias", categorias);
        Map<String, String> iconos = new HashMap<>();
        for (Categoria categoria : categorias) {
            iconos.put(categoria.name(), "img/" + categoria.name() + ".svg");
        }
        modelAndView.addObject("iconos", iconos);

        return modelAndView;
    }
}
