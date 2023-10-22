package org.example.DiscordRequestHandlers.Commands;

import org.example.HTTPHandler;
import org.example.users.Stock;
import org.example.users.StockPack;

public class GetOwnedStocksCommandAction extends CommandActionAdapter {
    public GetOwnedStocksCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        StockPack stocks = getBotUser().getStocksInProperty();
        if (stocks.isEmpty()){
            noActionAnswer = "You don't have any stocks in property.";
            return ActionResponce.NO_ACTION;
        }
        StringBuilder s = new StringBuilder();
        for(Stock stock : stocks){
            float price = HTTPHandler.getInstance().getStockPrice(stock.getSymbol());
            s.append(stock.getSymbol()).append(" - amount: ").append(stock.getAmount()).append("; price per stock: ").append(price).append("$").append("; total price: ").append(stock.getAmount()*price).append("$;\n");
        }
        successAnswer = s.toString();
        return ActionResponce.SUCCESS;
    }
}
