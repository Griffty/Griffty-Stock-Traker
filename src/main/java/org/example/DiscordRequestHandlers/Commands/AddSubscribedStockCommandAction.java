package org.example.DiscordRequestHandlers.Commands;

import org.example.HTTPHandler;

public class AddSubscribedStockCommandAction extends CommandActionAdapter {
    public AddSubscribedStockCommandAction(String commandID) {
        super(commandID);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOptions().get(0).getValue().getAsString().toUpperCase();
        if (HTTPHandler.getInstance().sendCompanyInfoRequest(symbol).body().contains("Cannot")){
            failAnswer = "Something went wrong, check if you entered correct stock.";
            return ActionResponce.FAIL;
        }
        if (getBotUser().subscribedStocks.contains(symbol)){
            noActionAnswer = "You already subscribed to this stock.";
            return ActionResponce.NO_ACTION;
        }
        getBotUser().subscribedStocks.add(symbol);
        successAnswer = "You subscribed to new stock.";
        return ActionResponce.SUCCESS;
    }
}
