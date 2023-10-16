package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPHandler {
    private static HTTPHandler self;
    private String lastSymbol;
    private HttpResponse<String> lastResponse;
    private final HttpClient client;
    public final String ALPHA_VANTAGE_APIKEY = API_TOKENS.ALPHA_VANTAGE_APIKEY;
    public final String RAPID_APIKEY = API_TOKENS.RAPID_APIKEY;

    public static HTTPHandler getInstance() {
        if (self != null){
            return self;
        }
        self = new HTTPHandler();
        return self;
    }
    private HTTPHandler(){
        client = HttpClient.newHttpClient();
        self = this;
    }
    public HttpResponse<String> sendCompanyInfoRequest(String symbol) {
        lastSymbol = symbol;
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("https://www.alphavantage.co/query?function=OVERVIEW&symbol="+symbol+"&apikey="+ALPHA_VANTAGE_APIKEY))
                .build();
        try {
            lastResponse =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return lastResponse;
        }     catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse<String> sendNewsRequest(String symbol){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://news-api14.p.rapidapi.com/search?q="+symbol+"_stock&country=us&language=en&pageSize=10&publisher=cnn.com%2Cbbc.com"))
                .header("X-RapidAPI-Key", "f77640da83mshdc20ecf7396de1bp12de81jsnb15f35dc1296")
                .header("X-RapidAPI-Host", "news-api14.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            lastResponse =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return lastResponse;
        }     catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse<String> sendStockPriceRequest(String symbol) {
        lastSymbol = symbol;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://realstonks.p.rapidapi.com/"+symbol))
                .header("X-RapidAPI-Key", RAPID_APIKEY)
                .header("X-RapidAPI-Host", "realstonks.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            return lastResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public float getStockPrice(String symbol) {
        HttpResponse<String> response = sendStockPriceRequest(symbol);
        try {
            return Float.parseFloat(response.body().split(":")[1].split(",")[0]);
        }catch (Exception e){
            if (response.body().contains("Invalid")){
                return -1;
            }else {
                e.printStackTrace();
            }
        }
        return -1;
    }
    public String getShortStockPriceInfo(String symbol){
        float price = getStockPrice(symbol);
        String s;
        if (price != -1){
            s = "The current value of "+symbol+" is "+ price+"$.";
        }else {
            s = "Cannot get stock price for this symbol";
        }
        return s;
    }
}
