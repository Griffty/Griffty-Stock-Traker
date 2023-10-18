package org.example;

import org.example.users.BotUser;

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
        float stockPrice = HTTPHandler.getInstance().getStockPrice(stock.symbol);
        float totalPrice = stock.amount * stockPrice;
        if (stockPrice == -1){
            return -2;
        }
        if (user.getMoney() < totalPrice){
            return -1;
        }

        user.setMoney(user.getMoney() - totalPrice);
        return user.AddStocksToProperty(stock);
    }
    public int sellStock(BotUser user, Stock stock){
        float stockPrice = HTTPHandler.getInstance().getStockPrice(stock.symbol);
        Stock stockInPack = user.getStockInProperty(stock.symbol);
        if (stockInPack == null){
            return -3;
        }
        float currentAmount = stockInPack.amount;
        if (stockPrice == -1){
            return -2;
        }
        if (currentAmount < stock.amount){
            return -1;
        }

        float totalPrice = stock.amount * stockPrice;
        user.setMoney(user.getMoney() + totalPrice);
        return user.RemoveStocksFromProperty(stock);
    }
}
