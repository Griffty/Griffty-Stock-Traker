package org.example.DiscordRequestHandlers;

public class GetSubscribedStocksCommand extends ResponseCommandAdapter{
    public GetSubscribedStocksCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        if (getBotUser().subscribedStocks.isEmpty()){
            noActionAnswer = "You don't have any stocks that you follow. To add them use /add_stock";
            return CommandResponse.NO_ACTION;
        }
        StringBuilder s = new StringBuilder();
        for (String article : getBotUser().subscribedStocks){
            s.append(article).append(", ");
        }
        successAnswer = "All stocks that you are subscribed to: \n" + s;
        return CommandResponse.SUCCESS;
    }
}
