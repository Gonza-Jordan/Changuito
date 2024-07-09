package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Promocion;
import com.tallerwebi.dominio.ServicioPromocion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPromocionTest {

    private ControladorPromocion controladorPromocion;
    private ServicioPromocion servicioPromocion;
    private ControladorPromocion controladorPromocionMock;

    @BeforeEach
    public void init() {
        this.servicioPromocion = mock(ServicioPromocion.class);
        this.controladorPromocion = new ControladorPromocion(this.servicioPromocion);
        this.controladorPromocionMock = mock(ControladorPromocion.class);

    }

    @Test
    public void queAlSolicitarLaPantallaDePromocionesSeMuestreLaVistaPromociones() {
        //Ejecucion
        ModelAndView modelAndView = controladorPromocion.irAPromociones();
        String viewName = modelAndView.getViewName();

        //Validacion
        assertThat(viewName, equalToIgnoringCase("promociones"));
    }

    @Test
    public void queAlIngresarALaPantallaDePromocionesSeMuestrenTodasLasPromociones() {
        //Ejecucion
        ModelAndView mav = this.controladorPromocion.irAPromociones();
        List<Promocion> promocionesObtenidas = (List<Promocion>) mav.getModel().get("promociones");

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("promociones"));
        assertThat(promocionesObtenidas, notNullValue());
    }

    @Test
    public void queAlIngresarALaPantallaDePromocionesMuestreUnMensajeDeErrorSiNoHayPromociones() {
        //Preparacion
        Map<String, Object> model = new HashMap<>();
        model.put("error", "No hay promociones");
        when(controladorPromocionMock.irAPromociones()).thenReturn(new ModelAndView("promociones", model));

        //Ejecucion
        ModelAndView mav = controladorPromocionMock.irAPromociones();

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("promociones"));
        assertThat(mav.getModel().get("promociones"), is(nullValue()));
        assertThat(mav.getModel().get("error"), is(notNullValue()));
        assertThat((String) mav.getModel().get("error"), is("No hay promociones"));
    }
}
