package org.example.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotUser {
    public String discordId;
    public String name;
    public ArrayList<String> subscribedStocks;
    private StockPack stocksInProperty = new StockPack();
    private float money = 0;
    public BotUser(){
    }
    public BotUser(String discordId, String name){
        this.discordId = discordId;
        this.name = name;
        subscribedStocks = new ArrayList<>();
    }
    public BotUser(String discordId, String name, ArrayList<String> subscribedStocks){
        this.discordId = discordId;
        this.name = name;
        this.subscribedStocks = subscribedStocks;
    }

    @Override
    public String toString() {
        return "User{" +
                "name= '" + name + "', " +
                "discordId='" + discordId + "', " +
                "money= '" + money + "', " +
                "subscribedStocks='" + subscribedStocks + "'" +
                "stocksInProperty='" + stocksInProperty + "'" +
                '}';
    }
    public Stock getStockInProperty(String symbol){
        Stock stock = stocksInProperty.get(symbol);
        return stock;
    }
    public int AddStocksToProperty(Stock stock){

        return stocksInProperty.addS(stock);
    }
    public int RemoveStocksFromProperty(Stock stock){
        return stocksInProperty.removeS(stock);
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public StockPack getStocksInProperty() {
        return stocksInProperty;
    }
    public void setStocksInProperty(StockPack stocks) {
        stocksInProperty = stocks;
    }

    @JsonIgnore
    public List<String> getAllStocksInProperty() {
        return new ArrayList<>(stocksInProperty.getAllStockSymbols());
    }

    @JsonIgnore
    private float totalMoney = -1;
    private long lastTotalMoneyUpdate = new Date().getTime();
    public float getTotalMoney() {
        if (totalMoney == -1 || lastTotalMoneyUpdate - new Date().getTime() > 1000 * 60){
            System.out.println(".");
            lastTotalMoneyUpdate = new Date().getTime();
            totalMoney = stocksInProperty.getAllStockPrice() + getMoney();
        }
        return totalMoney;
    }
}
