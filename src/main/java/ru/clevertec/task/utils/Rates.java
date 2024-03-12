package ru.clevertec.task.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.clevertec.task.enums.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static ru.clevertec.task.utils.Constants.API_KEY;
import static ru.clevertec.task.utils.Constants.JSON_OBJECT_NAME;

public class Rates {
    public static BigDecimal getExchangeRates(Currency fromCurrency, Currency toCurrency) throws IOException {
        URL url = getUrl(fromCurrency);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder content = getContent(reader);

        reader.close();
        connection.disconnect();

        JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();

        return jsonObject.getAsJsonObject(JSON_OBJECT_NAME).get(String.valueOf(toCurrency)).getAsBigDecimal();
    }

    private static StringBuilder getContent(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content;
    }

    private static URL getUrl(Currency fromCurrency) throws MalformedURLException {
        return new URL("https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + fromCurrency);
    }
}
