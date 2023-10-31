package org.example.DiscordRequestHandlers.Commands;

import org.example.users.Stock;
import org.example.TradeHandler;

public class SellStockCommandAction extends CommandActionAdapter {

    public SellStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();
        int amount = getOption("amount").getAsInteger();

        int ans = TradeHandler.getInstance().sellStock(getBotUser(), new Stock(symbol, amount));
        if (ans == -3){
            failAnswer = "You don't owe any of this stock.";
            return ActionResponce.FAIL;
        }if (ans == -2){
            failAnswer = "Something went wrong, check if you use right ticker.";
            return ActionResponce.FAIL;
        }
        if (ans == -1){
            noActionAnswer = "You don't have enough stock.";
            return ActionResponce.NO_ACTION;
        }
        successAnswer = "Deal finished. Now you have " + getBotUser().getStockInProperty(symbol).getAmount() + " " + symbol;
        return ActionResponce.SUCCESS;
    }
}
