package org.example;

import org.example.view.ViewApp;

import org.example.controller.ControllerApp;
import org.example.model.ModelApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Iniciando aplicación de clima...");
        ModelApp model = new ModelApp();
        ViewApp view = new ViewApp();
        new ControllerApp(model, view);
        view.setVisible(true);
        logger.info("Aplicación lista y visible para el usuario");
    }
}