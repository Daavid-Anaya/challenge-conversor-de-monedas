package com.david.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

import com.david.controller.Coordinador;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.cdimascio.dotenv.Dotenv;

public class ConsultarIntercambio {
    @SuppressWarnings("unused")
    private Coordinador coordinador;
    private URI direccion;
    private HttpClient client;
    private HttpRequest request;
    private Gson gson;
    private Dotenv dotenv = Dotenv.load();
    private String EXCHANGE_API_KEY = dotenv.get("EXCHANGE_API_KEY");

    public ConsultarIntercambio() {
        client = HttpClient.newHttpClient();
        
        gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public Moneda getMoneda(String moneda) {
        direccion = URI.create("https://v6.exchangerate-api.com/v6/" + EXCHANGE_API_KEY + "/latest/" + moneda);

        try {
            request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), Moneda.class);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró la moneda.");
        }
    }

    public String[] getTasasDeCambio(String moneda) {
        direccion = URI.create("https://v6.exchangerate-api.com/v6/" + EXCHANGE_API_KEY + "/latest/" + moneda);
        try {
            request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Moneda monedaObj = gson.fromJson(response.body(), Moneda.class);

            Collection<String> claves = monedaObj.rates.keySet();
            String[] tasasDeCambio = claves.toArray(new String[0]);

            return tasasDeCambio;
        } catch (Exception e) {
            throw new RuntimeException("No se encontró la moneda.");
        }
    }

    public double convertirMoneda(String deMoneda, String aMoneda, double cantidad) {
        Moneda moneda = getMoneda(deMoneda);
        Double tasaDesde = moneda.rates.get(deMoneda);
        Double tasaHasta = moneda.rates.get(aMoneda);

        if (tasaDesde == null || tasaHasta == null) {
            throw new IllegalArgumentException("Moneda no válida.");
        }

        return cantidad * tasaHasta;
    }
}