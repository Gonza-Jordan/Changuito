package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorProductoBuscadoTest {
    private ControladorProductoBuscado controladorProductoBuscado;

    @BeforeEach
    public void init() {
        this.controladorProductoBuscado = new ControladorProductoBuscado();
    }

    @Test
    public void queAlHacerClickEnBuscarMeMuestreLaVistaDeBuscado() {

        ModelAndView mav = this.controladorProductoBuscado.irAProductoBuscado();
        String viewName = mav.getViewName();
        Object productoBusc= mav.getModel().get("productoBuscado");

        assertThat(viewName, equalToIgnoringCase("productoBuscado"));


    }

}