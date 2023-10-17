package org.example.DiscordRequestHandlers;

import org.example.Stock;
import org.example.TradeHandler;

public class SellStockCommandAction extends CommandAdapterAction {

    public SellStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOptions().get(0).getValue().getAsString();
        int amount = getOptions().get(1).getValue().getAsInt();

        int ans = TradeHandler.getInstance().sellStock(getBotUser(), new Stock(symbol, amount));
        if (ans == -2){
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