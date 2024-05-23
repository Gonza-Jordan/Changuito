package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGeolocalizacionTest {

    private ServicioGeolocalizacion servicioGeolocalizacion ;
    private HttpClient httpClientMock;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        httpClientMock = mock(HttpClient.class);
        servicioGeolocalizacion = new ServicioGeolocalizacion(httpClientMock);
      objectMapper = new ObjectMapper();

    }

    @Test
    public void deberiaObtenerUbicacionBuscada() throws IOException, InterruptedException {
        String query = "some location";
        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String json = "[{ \"lat\": -34.676217, \"lon\": -58.563547 }]";
        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(httpResponseMock.statusCode()).thenReturn(200);
        when(httpResponseMock.body()).thenReturn(json);
        when(httpClientMock.send(any(HttpRequest.class),eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(httpResponseMock);

        JsonNode result = servicioGeolocalizacion.buscarUbicacion(query);




        assertThat(result, is(nullValue()));
        assertThat(result.get("lat").asDouble(), is(equalTo(-34.676217)));
        assertThat(result.get("lon").asDouble(), is(equalTo(-58.563547)));
    }

    }

