package org.example.model;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.io.BufferedReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelApp {
    private static final Logger logger = LoggerFactory.getLogger(ModelApp.class);

    private final String API_KEY = "075efe3587f004689f06c915e648c6cd";

    public JSONObject obtenerClima(String ciudad) throws Exception {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + ciudad + "&units=metric&appid=" + API_KEY;

        URL url = new URL(urlString);
        logger.debug("Llamando API del clima con URL: {}", urlString);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            logger.info("Clima obtenido correctamente para ciudad: {}", ciudad);
            return new JSONObject(content.toString());
        } catch (Exception e) {
            logger.error("Error al obtener el clima para ciudad: {}", ciudad, e);
            throw e;
        } finally {
            con.disconnect();
        }
    }
}