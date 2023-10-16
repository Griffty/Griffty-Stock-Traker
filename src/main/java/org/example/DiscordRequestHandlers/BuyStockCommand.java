package org.example.DiscordRequestHandlers;

import org.example.Stock;
import org.example.TradeHandler;

public class BuyStockCommand extends ResponseCommandAdapter{
    public BuyStockCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        String symbol = options.get(0).getValue().getAsString();
        int amount = options.get(1).getValue().getAsInt();

        int ans = TradeHandler.getInstance().buyStock(user, new Stock(symbol, amount));
        if (ans == -2){
            failAnswer = "Something went wrong, check if you use right ticker.";
            return CommandResponse.FAIL;
        }
        if (ans == -1){
            noActionAnswer = "You don't have enough money.";
            return CommandResponse.NO_ACTION;
        }
        successAnswer = "Deal finished. Now you have " + user.getStockInProperty(symbol).getAmount() + " " + symbol;
        return CommandResponse.SUCCESS;
    }
}
