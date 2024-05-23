package com.tallerwebi.dominio;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service("servicioSupermercado")
public class ServicioSupermercado {

    private final HttpClient httpClient;

    public ServicioSupermercado(HttpClient httpClient) {
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<Supermercado> obtenerSupermercados(Double latitud, Double longitud, Integer limite) throws IOException, InterruptedException {

        String url = "https://d3e6htiiul5ek9.cloudfront.net/prod/sucursales?lat=" + latitud + "&lng=" + longitud + "&limit=" + limite;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            List<Supermercado> supermercados = procesarDatosDeApi(response.body());
            return supermercados;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Supermercado> procesarDatosDeApi(String json) throws IOException, InterruptedException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode supermercadosNode = jsonNode.get("sucursales");

            List<Supermercado> supermercados = new ArrayList<>();
            for (JsonNode supermercadoNode : supermercadosNode) {
                Supermercado supermercado = objectMapper.treeToValue(supermercadoNode, Supermercado.class);
                supermercados.add(supermercado);
            }
            return supermercados;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
