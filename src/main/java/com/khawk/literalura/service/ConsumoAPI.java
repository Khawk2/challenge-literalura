package com.khawk.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khawk.literalura.dto.RespuestaAPI;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoAPI {
    
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    public RespuestaAPI obtenerDatos(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), RespuestaAPI.class);
            } else {
                throw new RuntimeException("Error en la respuesta de la API: " + response.statusCode());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }
    }
    
    public RespuestaAPI buscarLibroPorTitulo(String titulo) {
        String url = URL_BASE + "?search=" + titulo.replace(" ", "%20");
        return obtenerDatos(url);
    }
    
    public RespuestaAPI obtenerTodosLosLibros() {
        return obtenerDatos(URL_BASE);
    }
} 