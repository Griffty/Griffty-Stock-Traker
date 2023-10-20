package org.example.DiscordRequestHandlers.Modals;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.Stock;
import org.example.TradeHandler;

import java.util.List;

public class BuyStockModalAction extends ModalActionAdapter{

    public BuyStockModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol");
        int amount = Integer.parseInt(getOption("amount"));

        int ans = TradeHandler.getInstance().buyStock(getBotUser(), new Stock(symbol, amount));
        if (ans == -2){
            failAnswer = "Something went wrong, check if you use right ticker.";
            return ActionResponce.FAIL;
        }
        if (ans == -1){
            noActionAnswer = "You don't have enough money.";
            return ActionResponce.NO_ACTION;
        }
        successAnswer = "Deal finished. Now you have " + getBotUser().getStockInProperty(symbol).getAmount() + " " + symbol;
        return ActionResponce.SUCCESS;
    }
}
