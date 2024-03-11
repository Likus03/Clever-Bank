package ru.clevertec.task.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = "/converter")
public class Converter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = "EUR";
        String to = "USD";

        URL url = new URL("https://v6.exchangerate-api.com/v6/bdd1af0bd03086e428c68bfb/latest/"+from);
//        URL url = new URL("https://api.fastforex.io/fetch-multi?from="+from+"&to="+to+"&api_key=0b26dfd764-4c7c0204b8-sa7aqg");
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

        String value = jsonObject.getAsJsonObject("conversion_rates").get("USD").getAsString();

        resp.getWriter().write("Value from JSON: " + value);
    }

}
