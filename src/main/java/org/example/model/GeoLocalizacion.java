package org.example.model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocalizacion {
    private static final Logger logger = LoggerFactory.getLogger(GeoLocalizacion.class);

    public static String obtenerCiudadActual() {
        try {
            logger.info("Detectando ciudad actual por IP...");
            URL url = new URL("http://ip-api.com/json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            JSONObject response = new JSONObject(content.toString());
            if ("success".equals(response.getString("status"))) {
                String ciudad = response.getString("city");
                logger.info("Ciudad detectada: {}", ciudad);
                return ciudad;
            }
        } catch (Exception e) {
            logger.error("No se pudo obtener la ubicación: ", e);
        }
        return "Bogotá"; // valor por defecto si falla
    }
}
