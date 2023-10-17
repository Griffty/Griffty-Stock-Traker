package org.example.DiscordRequestHandlers;

import org.example.WebScraper;

import java.util.ArrayList;

public class GetNewsCommandAction extends CommandAdapterAction {
    public GetNewsCommandAction(String commandID) {
        super(commandID);
    }

    @Override
    public ActionResponce execute() {
        ArrayList<String> newsList = WebScraper.getNewsForSpecificSymbol(getOptions().get(0).getValue().getAsString(), getOptions().get(1).getValue().getAsInt());
        StringBuilder s = new StringBuilder();
        for (String article : newsList){
            s.append(article).append(",\n");
        }
        successAnswer = s.toString();
        failAnswer = s.toString();
        if (newsList.get(0).contains("Cannot")){
            return ActionResponce.FAIL;
        }
        return ActionResponce.SUCCESS;
    }
}
