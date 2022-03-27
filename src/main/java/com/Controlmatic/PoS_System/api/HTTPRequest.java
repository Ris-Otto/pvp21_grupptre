package com.Controlmatic.PoS_System.api;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class HTTPRequest {

    private String port;

    //makes a post request with desired urlString
    //urlString is to be entered in form http://localhost:900X/XXXX/XXXX
    public static void makePostRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        System.out.println(http.getResponseCode());
    }

    //makes a post request with a dataKey and dataValue
    //urlString is to be entered in form http://localhost:900X/XXXX/XXXX and dataKey eg: "amount" dataValue eg: "69"
    public static void makePostRequest(String urlString, String dataKey, String dataValue) throws IOException {

        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        String data = dataKey + "=" + dataValue;
        http.setDoOutput(true);
        http.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(http.getResponseCode());
    }

    public static void makePostRequest(String urlString, String objectXML) throws IOException {

        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/xml");
        http.getOutputStream().write(objectXML.getBytes(StandardCharsets.UTF_8));
        System.out.println(http.getResponseCode());
    }

    //makes a get request with desired urlString
    //urlString is to be entered in form http://localhost:900X/XXXX/XXXX
    public static String makeGetRequest(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(http.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    /*public int getPort() {
        return port;
    }*/
}
