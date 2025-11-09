package com.david.controller;

import com.david.model.ConsultarIntercambio;
import com.david.model.Moneda;
import com.david.view.VentanaPrincipal;

public class Coordinador {
    @SuppressWarnings("unused")
    private VentanaPrincipal ventana;
    private ConsultarIntercambio consulta;

    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    public void setConsulta(ConsultarIntercambio consulta) {
        this.consulta = consulta;
    }

    public Moneda getMoneda(String moneda) {
        return consulta.getMoneda(moneda);
    }

    public String[] getTasas(String moneda) {
        return consulta.getTasasDeCambio(moneda);
    }

    public double convertirMoneda(String deMoneda, String aMoneda, double cantidad) {
        return consulta.convertirMoneda(deMoneda, aMoneda, cantidad);
    }
}
