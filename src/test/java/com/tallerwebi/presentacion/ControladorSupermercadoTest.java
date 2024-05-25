package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSupermercado;
import com.tallerwebi.dominio.Supermercado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
public class ControladorSupermercadoTest {

    private ServicioSupermercado servicioSupermercado;
    private ControladorSupermercado controladorSupermercado;

    @BeforeEach
    public void init(){

        this.servicioSupermercado = mock(ServicioSupermercado.class);
        this.controladorSupermercado = new ControladorSupermercado(this.servicioSupermercado);}


    @Test
    public void queAlHacerClickEnSupermercadoVayaAlaVistaSupermercado() throws IOException, InterruptedException {

        double latitud = -34.67055556;
        double longitud = -58.56277778;


        ModelAndView mav = this.controladorSupermercado.irASupermercados(latitud,longitud);
        String view = mav.getViewName();
        Object supermercados= mav.getModel().get("supermercados");

        assertThat(view,equalToIgnoringCase("supermercados"));

    }

    @Test
    public void queAlHacerClickEnSupermercadoVayaAlaVistaSupermercadoYMuestreLaListaDeSuperCercano() throws IOException, InterruptedException {

        Double latitud = -34.699416;
        Double longitud = -58.566259;
        List<Supermercado> supermercadosEsperados = new ArrayList<>();
       supermercadosEsperados.add(new Supermercado("Super1"));
       supermercadosEsperados.add(new Supermercado("Super2"));

        when(servicioSupermercado.obtenerSupermercados(latitud, longitud, 30)).thenReturn(supermercadosEsperados);

        ModelAndView mav = controladorSupermercado.irASupermercados(latitud, longitud);
        String view = mav.getViewName();
        List<Supermercado> supermercadosObtenidos = (List<Supermercado>) mav.getModel().get("supermercados");


        assertThat(view, equalTo("supermercados"));
        assertThat(supermercadosObtenidos.size(), equalTo(2));
    }
}
