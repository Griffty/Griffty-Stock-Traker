package org.example.DiscordRequestHandlers;

public class RemoveSubscribedStockCommandAction extends CommandAdapterAction {
    public RemoveSubscribedStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOptions().get(0).getValue().getAsString().toUpperCase();
        if (!getBotUser().subscribedStocks.contains(symbol)){
            failAnswer = "Can't remove this stock, because you are not subscribed to it.";
            return ActionResponce.FAIL;
        }
        successAnswer = "You unsubscribed from this stock";
        return ActionResponce.SUCCESS;
    }
}
