package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ServicioGeolocalizacion {

    private final HttpClient httpClient;

    public ServicioGeolocalizacion() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public JsonNode buscarUbicacion(String query) throws IOException, InterruptedException {
        query = URLEncoder.encode(query, "UTF-8");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://nominatim.openstreetmap.org/search?format=json&q=" + query))
                .build();

        HttpResponse<String> respuesta = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() != 200) {
            throw new IOException("Código inesperado " + respuesta.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode repuestaJson = objectMapper.readTree(respuesta.body());

        if (repuestaJson.isArray() && repuestaJson.size() > 0) {
            return repuestaJson.get(0);
        }

        return null;
    }
}

