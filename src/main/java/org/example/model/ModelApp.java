package org.example.model;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.io.BufferedReader;

public class ModelApp {
    private final String API_KEY = "075efe3587f004689f06c915e648c6cd";

    public JSONObject obtenerClima(String ciudad) throws Exception {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + ciudad + "&units=metric&appid=" + API_KEY;

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        return new JSONObject(content.toString());
    }
}
