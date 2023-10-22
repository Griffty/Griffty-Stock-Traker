package org.example.users;

public class Stock {
    protected String symbol = "";
    protected int amount = 0;
    public Stock(){
    }
    Stock(String s){
        symbol = s;
        amount = 0;
    }
    public Stock(String s, int a){
        symbol = s;
        amount = a;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", amount=" + amount +
                '}';
    }
}
