package org.example.DiscordRequestHandlers;

import org.example.HTTPHandler;

public class GetStockCommandAction extends CommandActionAdapter {
    public GetStockCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        String priceInfo = HTTPHandler.getInstance().getShortStockPriceInfo(getOptions().get(0).getValue().getAsString());
        successAnswer = priceInfo;
        failAnswer = priceInfo;
        if (priceInfo.contains("Cannot")){
            return ActionResponce.FAIL;
        }
        return ActionResponce.SUCCESS;
    }
}
