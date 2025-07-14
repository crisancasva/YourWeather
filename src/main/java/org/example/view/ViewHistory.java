package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewHistory extends JFrame{
    public JTable tablaHistorial;
    public DefaultTableModel tablaModel;

    public ViewHistory() {
        setTitle("Historial de Consultas");
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnas = {"Ciudad", "Temperatura (°C)", "Humedad (%)", "Fecha"};
        tablaModel = new DefaultTableModel(columnas, 0);
        tablaHistorial = new JTable(tablaModel);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        add(scrollPane, BorderLayout.CENTER);
    }
}
