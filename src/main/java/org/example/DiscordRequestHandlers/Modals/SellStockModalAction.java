package org.example.DiscordRequestHandlers.Modals;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.users.Stock;
import org.example.TradeHandler;

import java.util.List;

public class SellStockModalAction extends ModalActionAdapter{
    public SellStockModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol");
        int amount = Integer.parseInt(getOption("amount"));

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
