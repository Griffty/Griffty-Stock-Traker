package org.example.DiscordRequestHandlers;

public class RemoveSubscribedStockCommand extends ResponseCommandAdapter{
    public RemoveSubscribedStockCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        String symbol = options.get(0).getValue().getAsString().toUpperCase();
        if (!user.subscribedStocks.contains(symbol)){
            failAnswer = "Can't remove this stock, because you are not subscribed to it.";
            return CommandResponse.FAIL;
        }
        successAnswer = "You unsubscribed from this stock";
        return CommandResponse.SUCCESS;
    }
}
