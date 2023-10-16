package org.example.DiscordRequestHandlers;

import org.example.HTTPHandler;

public class GetStockCommand extends ResponseCommandAdapter {
    public GetStockCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        String priceInfo = HTTPHandler.getInstance().getShortStockPriceInfo(options.get(0).getValue().getAsString());
        successAnswer = priceInfo;
        failAnswer = priceInfo;
        if (priceInfo.contains("Cannot")){
            return CommandResponse.FAIL;
        }
        return CommandResponse.SUCCESS;
    }
}
