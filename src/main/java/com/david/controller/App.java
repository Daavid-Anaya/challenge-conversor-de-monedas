package com.david.controller;

import com.david.model.ConsultarIntercambio;
import com.david.view.VentanaPrincipal;

public class App {
    public void iniciarSistema() {
        // Instanciar clases
        VentanaPrincipal ventana = new VentanaPrincipal();
        ConsultarIntercambio consulta = new ConsultarIntercambio();
        Coordinador coordinador = new Coordinador();
        
        // Relacionar clases con el coordinador
        ventana.setCoordinador(coordinador);
        consulta.setCoordinador(coordinador);

        // Relacionar el coordinador con las clases
        coordinador.setVentana(ventana);
        coordinador.setConsulta(consulta);

        // Iniciar componentes de la ventana
        ventana.iniciarComponentes();
    }
}
