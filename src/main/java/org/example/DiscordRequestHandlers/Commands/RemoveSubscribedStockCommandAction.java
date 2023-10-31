package org.example.DiscordRequestHandlers.Commands;

public class RemoveSubscribedStockCommandAction extends CommandActionAdapter {
    public RemoveSubscribedStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();
        if (!getBotUser().subscribedStocks.contains(symbol)){
            failAnswer = "Can't remove this stock, because you are not subscribed to it.";
            return ActionResponce.FAIL;
        }
        successAnswer = "You unsubscribed from this stock";
        return ActionResponce.SUCCESS;
    }
}
