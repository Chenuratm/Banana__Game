package com.banana.game.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FactService {

    public String getFact() {

        URL url = new URL("http://numbersapi.com/random/trivia");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String fact = reader.readLine();
        reader.close();

        return fact;

    }
}