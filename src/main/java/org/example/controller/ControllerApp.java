package org.example.controller;

import org.example.model.ModelApp;
import org.example.model.Repositorio;
import org.example.view.ViewHistory;
import org.json.JSONObject;
import org.example.view.ViewApp;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerApp {
    private final ModelApp model;
    private final ViewApp view;
    private final Repositorio repo;

    public ControllerApp(ModelApp model, ViewApp view) {
        this.model = model;
        this.view = view;
        this.repo = new Repositorio();

        this.view.buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciudad = view.ciudadTextField.getText().trim();
                if (ciudad.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Por favor ingresa una ciudad.");
                    return;
                }

                try {
                    JSONObject clima = model.obtenerClima(ciudad);
                    double temperatura = clima.getJSONObject("main").getDouble("temp");
                    int humedad = clima.getJSONObject("main").getInt("humidity");

                    view.temperaturaLabel.setText("Temperatura: " + temperatura + " °C");
                    view.humedadLabel.setText("Humedad: " + humedad + " %");

                    // Guardar en MongoDB
                    repo.guardarConsulta(ciudad, temperatura, humedad);

                } catch (Exception ex) {
                    view.temperaturaLabel.setText("Temperatura: error");
                    view.humedadLabel.setText("Humedad: error");
                    JOptionPane.showMessageDialog(view,
                            "No se pudo obtener el clima para la ciudad: " + ciudad,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.view.verHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewHistory viewHistory = new ViewHistory();

                for (Document doc : repo.obtenerHistorial()) {
                    String ciudad = doc.getString("ciudad");
                    Double temperatura = doc.getDouble("temperatura");
                    Integer humedad = doc.getInteger("humedad");
                    String fecha = doc.getString("fecha");

                    Object[] fila = {ciudad, temperatura, humedad, fecha};
                    viewHistory.tablaModel.addRow(fila);
                }

                viewHistory.setVisible(true);
            }
        });

    }
}
