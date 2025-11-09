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
        return null;
    }

    public String[] getTasasDeCambio(String moneda) {
        return null;
    }

    public double convertirMoneda(String deMoneda, String aMoneda, double cantidad) {
        return 0;
    }
}