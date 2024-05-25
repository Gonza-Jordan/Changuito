package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioSupermercadoTest  {

    private HttpResponse<String> httpResponseMock;
    private ServicioSupermercado servicioSupermercado;
    private HttpClient httpClientMock;

    @BeforeEach
    public void init(){
        this.httpClientMock = mock(HttpClient.class);
        this.httpResponseMock = mock(HttpResponse.class);
       this.servicioSupermercado = new ServicioSupermercado(this.httpClientMock);

    }
    @Test
    public void queSePuedaDevolverSupermercados() throws IOException, InterruptedException {
        Double latitud = -34.699416;
        Double longitud = -58.566259;
      //  String url = "https://d3e6htiiul5ek9.cloudfront.net/prod/sucursales?lat=" + latitud + "&lng=" + longitud + "&limit=" + 30;
//        List<Supermercado> supermercadosEsperados = new ArrayList<>();
//        supermercadosEsperados.add(new Supermercado("Super1"));
//        supermercadosEsperados.add(new Supermercado("Super2"));
        String jsonResponse = "{ \"sucursales\": [{ \"distanciaNumero\": 1.5, \"distanciaDescripcion\": \"Cerca\", \"banderaId\": 123, \"lat\": \"-34.603722\", \"lng\": \"-58.381592\", \"sucursalNombre\": \"Supermercado1\", \"id\": \"1\", \"sucursalTipo\": \"Hipermercado\", \"provincia\": \"Buenos Aires\", \"direccion\": \"Calle Falsa 123\", \"banderaDescripcion\": \"Super\", \"localidad\": \"Capital Federal\", \"comercioRazonSocial\": \"Supermercado SA\", \"comercioId\": 456, \"sucursalId\": \"S1\" }] }";



     //  when(servicioSupermercado.obtenerSupermercados(latitud,longitud,30)).thenReturn(supermercadosEsperados);
        //when(httpClientMock.send(HttpRequest.class, HttpResponse.BodyHandlers.class)).thenReturn(httpResponseMock);
        when(httpResponseMock.body()).thenReturn(jsonResponse);
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponseMock);



        List<Supermercado> superObtenido = servicioSupermercado.obtenerSupermercados(latitud,longitud,1);
        assertThat(superObtenido, is(notNullValue()));
        assertThat(superObtenido, hasSize(1));
        assertThat(superObtenido.get(0).getSucursalNombre(), is("Supermercado1"));

//        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
//                .thenReturn(httpResponseMock);
//        when(httpResponseMock.body()).thenReturn(jsonResponse);
//
//        List<Supermercado> supermercados = servicioSupermercado.obtenerSupermercados(10.0, 20.0, 5);
//
//        assertThat(supermercados, is(notNullValue()));
//        assertThat(supermercados, hasSize(1));
//        assertThat(supermercados.get(0).getSucursalNombre(), is("Supermercado1"));

    }








}
