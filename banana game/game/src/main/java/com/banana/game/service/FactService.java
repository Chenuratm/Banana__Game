package com.banana.game.service;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FactService {

    public String getFact() {

        try {

            URL url = new URL("https://v2.jokeapi.dev/joke/Any");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(response.toString());

            String type = json.getString("type");

            if (type.equals("single")) {
                return json.getString("joke");
            } else {
                return json.getString("setup") + " - " + json.getString("delivery");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Why did the banana go to school? Because it wanted to become a smart fruit!";
        }
    }
}