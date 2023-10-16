package org.example.DiscordRequestHandlers;

import org.example.HTTPHandler;

public class AddSubscribedStockCommand extends ResponseCommandAdapter {
    public AddSubscribedStockCommand(String commandID) {
        super(commandID);
    }

    @Override
    public CommandResponse execute() {
        String symbol = options.get(0).getValue().getAsString().toUpperCase();
        if (HTTPHandler.getInstance().sendCompanyInfoRequest(symbol).body().contains("Cannot")){
            failAnswer = "Something went wrong, check if you entered correct stock.";
            return CommandResponse.FAIL;
        }
        if (user.subscribedStocks.contains(symbol)){
            noActionAnswer = "You already subscribed to this stock.";
            return CommandResponse.NO_ACTION;
        }
        user.subscribedStocks.add(symbol);
        successAnswer = "You subscribed to new stock.";
        return CommandResponse.SUCCESS;
    }
}
