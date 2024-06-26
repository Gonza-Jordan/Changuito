package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tallerwebi.dominio.SupermercadoAPI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorAPI {

    private final HttpClient httpClient;

    public ControladorAPI() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @GetMapping("/get")
    public List<SupermercadoAPI> obtenerSucursales() {
        double latitud = -34.67055556;
        double longitud = -58.56277778;
        int limite = 30;

        String url = "https://d3e6htiiul5ek9.cloudfront.net/prod/sucursales?lat=" + latitud + "&lng=" + longitud + "&limit=" + limite;

        try {
            // Crear la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Realizar la solicitud HTTP
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Procesar la respuesta JSON y retornar la lista de sucursales
            List<SupermercadoAPI> supermercados = procesarDatosDeApi(response.body());

            return supermercados;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<SupermercadoAPI> procesarDatosDeApi(String json) {
        try {
            // Procesar el JSON y construir la lista de sucursales
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode sucursalesNode = jsonNode.get("sucursales");

            List<SupermercadoAPI> sucursales = new ArrayList<>();
            for (JsonNode sucursalNode : sucursalesNode) {
                SupermercadoAPI supermercado = objectMapper.treeToValue(sucursalNode, SupermercadoAPI.class);
                sucursales.add(supermercado);
            }

            return sucursales;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}





