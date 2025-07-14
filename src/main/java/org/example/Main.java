package org.example;

import org.example.view.ViewApp;

import org.example.controller.ControllerApp;
import org.example.model.ModelApp;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ModelApp model = new ModelApp();
        ViewApp view = new ViewApp();
        new ControllerApp(model, view);
        view.setVisible(true);
    }
}