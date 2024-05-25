package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSupermercado;
import com.tallerwebi.dominio.Supermercado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorSupermercadoTest {
    private ControladorSupermercado controladorSupermercado;
    private ServicioSupermercado servicioSupermercado;

    @BeforeEach
    public void init(){ this.controladorSupermercado = new ControladorSupermercado(new ServicioSupermercado());}

    @Test
    public void queAlHacerClickEnSupermercadoVayaAlaVistaSupermercado() throws IOException, InterruptedException {

        double latitud = -34.67055556;
        double longitud = -58.56277778;


        ModelAndView mav = this.controladorSupermercado.irASupermercados(latitud,longitud);
        String view = mav.getViewName();
        Object supermercados= mav.getModel().get("supermercados");

        assertThat(view,equalToIgnoringCase("supermercados"));

    }
//    @Test
//    public void queAlHacerClickEnSupermercadoVayaAlaVistaSupermercadoYMuestreLaListaDeSuperCercano() throws IOException, InterruptedException {
//
//       double latitud = -34.67055556;
//        double longitud = -58.56277778;
//        List<Supermercado> supermercadosEsperados = servicioSupermercado.obtenerSupermercados(latitud, longitud, 30);
//
//
//        ModelAndView mav = this.controladorSupermercado.irASupermercados(latitud,longitud);
//        String view = mav.getViewName();
//
//        List<Supermercado> supermercadosObtenidos= (List<Supermercado>) mav.getModel().get("supermercados");
//
//        assertThat(supermercadosObtenidos,equalTo(supermercadosEsperados));
//
//    }

}
