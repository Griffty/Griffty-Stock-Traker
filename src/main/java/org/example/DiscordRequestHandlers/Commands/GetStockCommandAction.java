package org.example.DiscordRequestHandlers.Commands;

import org.example.HTTPHandler;

public class GetStockCommandAction extends CommandActionAdapter {
    public GetStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String priceInfo = HTTPHandler.getInstance().getShortStockPriceInfo(getOption("symbol").getAsString());
        successAnswer = priceInfo;
        failAnswer = priceInfo;
        if (priceInfo.contains("Cannot")){
            return ActionResponce.FAIL;
        }
        return ActionResponce.SUCCESS;
    }
}
