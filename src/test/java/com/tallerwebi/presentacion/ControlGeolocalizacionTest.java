package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.ServicioGeolocalizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControlGeolocalizacionTest {

@Mock
private ServicioGeolocalizacion servicioGeolocalizacion;

@InjectMocks
private ControladorGeolocalizacion controladorGeolocalizacion;

private MockMvc mockMvc;

@BeforeEach
public void setUp() {
    MockitoAnnotations.openMocks(this);

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");

    mockMvc = MockMvcBuilders.standaloneSetup(controladorGeolocalizacion)
            .setViewResolvers(viewResolver)
            .build();
}

@Test
public void queLocaliceRedirijaASupermercado() throws Exception {

    String query = "some location";
    ObjectMapper mapper = new ObjectMapper();
    String json = "[{ \"lat\": -34.676217, \"lon\": -58.563547 }]";
    JsonNode jsonNode = mapper.readTree(json);

    when(servicioGeolocalizacion.buscarUbicacion(query)).thenReturn(jsonNode.get(0));

    // Act & Assert
    mockMvc.perform(post("/search").param("query", query))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/supermercados?latitud=-34.676217&longitud=-58.563547"));

    verify(servicioGeolocalizacion, times(1)).buscarUbicacion(query);
}

@Test
public void queNoEncuentreYRedirija() throws Exception {
    // Arrange
    String query = "some location";

    when(servicioGeolocalizacion.buscarUbicacion(query)).thenThrow(IOException.class);

    // Act & Assert
    mockMvc.perform(post("/search").param("query", query))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/supermercados"));

    verify(servicioGeolocalizacion, times(1)).buscarUbicacion(query);
}
}