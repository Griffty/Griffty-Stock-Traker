package org.example.users;

import java.text.SimpleDateFormat;
import java.util.Date;

public record Transaction(long dateCreated, Stock stock, String userName, float perStockPrice, float totalPrice, boolean bought) {
    public String getTransactionInfo(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date()) + ": " + userName + " " + (bought ? "bought" : "sold") + " " + stock.getAmount()
                + " " + stock.symbol + "; stock price: " + perStockPrice + "; total: " + totalPrice;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "dateCreated=" + dateCreated +
                ", stock=" + stock +
                ", perStockPrice=" + perStockPrice +
                ", totalPrice=" + totalPrice +
                ", bought=" + bought +
                '}';
    }
}
