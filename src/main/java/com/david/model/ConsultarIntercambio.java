package com.david.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

import javax.swing.JOptionPane;

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
    private Moneda monedaObj;

    public ConsultarIntercambio() {
        this.client = HttpClient.newHttpClient();
        
        this.gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();
        this.monedaObj = getMoneda("MXN");
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
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la moneda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("No se encontró la moneda.");
        }
    }

    public Double getRate(String moneda) {
        return monedaObj.rates.get(moneda);
    }

    public String getTimeLastUpdateUtc() {
        return monedaObj.timeLastUpdateUtc;
    }

    public String[] getTasasDeCambio() {
        Collection<String> claves = monedaObj.rates.keySet();
        String[] tasasDeCambio = claves.toArray(new String[0]);

        return tasasDeCambio;
    }

    public String convertirMoneda(String deMoneda, String aMoneda, double cantidad) {
        Double tasaDesde = monedaObj.rates.get(deMoneda);
        Double tasaHasta = monedaObj.rates.get(aMoneda);
        double conversion = 0;

        if (tasaDesde == null || tasaHasta == null) {
            JOptionPane.showMessageDialog(null, "Moneda no válida para la conversión.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Moneda no válida.");
        }

        conversion = (tasaHasta / tasaDesde) * cantidad;

        return String.format("%.5f", conversion);
    }
}