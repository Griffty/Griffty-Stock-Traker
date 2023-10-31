package org.example;

import org.example.users.BotUser;
import org.example.users.Stock;

public class TradeHandler {
    private static TradeHandler self;
    public static TradeHandler getInstance() {
        if (self == null){
            self = new TradeHandler();
        }
        return self;
    }
    private TradeHandler(){

    }

    public int buyStock(BotUser user, Stock stock){
        float stockPrice = HTTPHandler.getInstance().getStockPrice(stock.getSymbol());
        float totalPrice = stock.getAmount() * stockPrice;
        if (stockPrice == -1){
            return -2;
        }
        if (user.getMoney() < totalPrice){
            return -1;
        }

        user.setMoney(user.getMoney() - totalPrice);
        TransactionLogger.getInstance().logTransaction(user, stock, stockPrice, true);
        return user.AddStocksToProperty(stock);
    }
    public int sellStock(BotUser user, Stock stock){
        float stockPrice = HTTPHandler.getInstance().getStockPrice(stock.getSymbol());
        Stock stockInPack = user.getStockInProperty(stock.getSymbol());
        if (stockInPack == null){
            return -3;
        }
        float currentAmount = stockInPack.getAmount();
        if (stockPrice == -1){
            return -2;
        }
        if (currentAmount < stock.getAmount()){
            return -1;
        }

        float totalPrice = stock.getAmount() * stockPrice;
        user.setMoney(user.getMoney() + totalPrice);
        TransactionLogger.getInstance().logTransaction(user, stock, stockPrice, false);
        return user.RemoveStocksFromProperty(stock);
    }
}
