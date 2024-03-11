package ru.clevertec.task.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rates {
    public static BigDecimal getExchangeRates(String from, String to) throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6/bdd1af0bd03086e428c68bfb/latest/" + from);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();
        connection.disconnect();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(content.toString()).getAsJsonObject();

        return jsonObject.getAsJsonObject("conversion_rates").get(to).getAsBigDecimal();
    }
}
