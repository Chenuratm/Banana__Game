package com.banana.game.service;

import com.banana.game.model.BananaQuestion;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class BananaApiService {

    // API base URL
    private static final String API_URL =
            "https://marcconrad.com/uob/banana/api.php";

    /*
     * Get question based on difficulty
     */
    public BananaQuestion getQuestion(int difficulty) {

        try {

            // Create URL with difficulty parameter
            URL url = new URL(API_URL + "?difficulty=" + difficulty);

            // Open connection
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            // Read response from API
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

            String response = reader.readLine();

            // Convert response to JSON
            JSONObject json = new JSONObject(response);

            // Extract data
            String image = json.getString("question");
            int solution = json.getInt("solution");

            // Return object
            return new BananaQuestion(image, solution);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}