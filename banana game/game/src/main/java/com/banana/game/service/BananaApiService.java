package com.banana.game.service;

import com.banana.game.model.BananaQuestion;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BananaApiService {

    private static final String API_URL =
            "https://marcconrad.com/uob/banana/api.php";

    public BananaQuestion getQuestion(int difficulty) {

        try {

            URL url = new URL(API_URL + "?difficulty=" + difficulty);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

            String response = reader.readLine();

            JSONObject json = new JSONObject(response);

            String image = json.getString("question");
            int solution = json.getInt("solution");

            return new BananaQuestion(image, solution);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}