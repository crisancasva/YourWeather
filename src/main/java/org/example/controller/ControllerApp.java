package org.example.controller;

import org.example.model.ModelApp;
import org.example.model.Repositorio;
import org.example.view.ViewHistory;
import org.example.model.GeoLocalizacion;
import org.json.JSONObject;
import org.example.view.ViewApp;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerApp {

    private static final Logger logger = LoggerFactory.getLogger(ControllerApp.class);

    private final ModelApp model;
    private final ViewApp view;
    private final Repositorio repo;

    public ControllerApp(ModelApp model, ViewApp view) {
        this.model = model;
        this.view = view;
        this.repo = new Repositorio();

        mostrarClimaInicial();

        this.view.buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClima(view.ciudadTextField.getText().trim());
            }
        });
        this.view.verHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Abriendo historial de consultas");

                ViewHistory viewHistory = new ViewHistory();

                for (Document doc : repo.obtenerHistorial()) {
                    Document sys = (Document) doc.get("sys");
                    String pais = sys.getString("country");
                    String ciudad = doc.getString("name");
                    Document main = (Document) doc.get("main");
                    Number tempRaw = main.get("temp", Number.class);
                    Double temperatura = tempRaw != null ? tempRaw.doubleValue() : null;
                    Integer humedad = main.getInteger("humidity");
                    String fecha = doc.getString("fecha");

                    Object[] fila = {pais, ciudad, temperatura, humedad, fecha};
                    viewHistory.tablaModel.addRow(fila);
                    logger.debug("Historial → {}, {} → {} °C, {} %, fecha {}",
                            pais, ciudad, temperatura, humedad, fecha);
                }

                viewHistory.setVisible(true);
                logger.info(" Historial mostrado correctamente");
            }
        });

    }
    private void mostrarClimaInicial() {
        String ciudad = GeoLocalizacion.obtenerCiudadActual();
        buscarClima(ciudad); // reutilizamos el mismo flujo de búsqueda
    }

    private void buscarClima(String ciudad){
        if (ciudad == null || ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor ingresa una ciudad.");
            logger.warn("El usuario intentó buscar clima sin ingresar ciudad");
            return;
        }

        logger.info("Buscando clima para la ciudad: {}", ciudad);

        try {
            JSONObject clima = model.obtenerClima(ciudad);

            String pais = clima.getJSONObject("sys").getString("country");
            String ciudadObtenida = clima.getString("name");
            double temperatura = clima.getJSONObject("main").getDouble("temp");
            int humedad = clima.getJSONObject("main").getInt("humidity");

            view.paisLabel.setText("Pais: " + pais);
            view.ciudadLabel.setText("Ciudad: " + ciudadObtenida);
            view.temperaturaLabel.setText("Temperatura: " + temperatura + " °C");
            view.humedadLabel.setText("Humedad: " + humedad + " %");
            logger.info("Clima obtenido: {}, {} → {} °C, {} % humedad",
                    pais, ciudadObtenida, temperatura, humedad);


            // Guardar en MongoDB
            repo.guardarConsulta(clima);
            logger.debug("Consulta guardada en MongoDB para ciudad: {}", ciudad);

        } catch (Exception ex) {
            view.temperaturaLabel.setText("Temperatura: error");
            view.humedadLabel.setText("Humedad: error");
            JOptionPane.showMessageDialog(view,
                    "No se pudo obtener el clima para la ciudad: " + ciudad,
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Error al obtener clima para ciudad: {}", ciudad, ex);
        }
    }
}
