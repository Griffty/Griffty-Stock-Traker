package org.example.DiscordRequestHandlers;

public class GetSubscribedStocksCommandAction extends CommandActionAdapter {
    public GetSubscribedStocksCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        if (getBotUser().subscribedStocks.isEmpty()){
            noActionAnswer = "You don't have any stocks that you follow. To add them use /add_stock";
            return ActionResponce.NO_ACTION;
        }
        StringBuilder s = new StringBuilder();
        for (String article : getBotUser().subscribedStocks){
            s.append(article).append(", ");
        }
        successAnswer = "All stocks that you are subscribed to: \n" + s;
        return ActionResponce.SUCCESS;
    }
}
