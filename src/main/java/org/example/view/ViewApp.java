package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ViewApp extends JFrame{
    public JTextField ciudadTextField = new JTextField();
    public JButton buscarButton = new JButton("Buscar");
    public JLabel paisLabel = new JLabel("Pais: ");
    public JLabel ciudadLabel = new JLabel("Ciudad: ");
    public JLabel temperaturaLabel = new JLabel("Temperatura: ");
    public JLabel humedadLabel = new JLabel("Humedad: ");
    public JButton verHistorial = new JButton("Ver Historial");

    public ViewApp() {
        setTitle("Clima App");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 1, 5, 5));
        panel.add(new JLabel("Dime la ciudad:"));
        panel.add(ciudadTextField);
        panel.add(buscarButton);
        panel.add(paisLabel);
        panel.add(ciudadLabel);
        panel.add(temperaturaLabel);
        panel.add(humedadLabel);
        panel.add(verHistorial);

        add(panel);
    }
}
