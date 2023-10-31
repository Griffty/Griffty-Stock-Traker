package org.example.DiscordRequestHandlers.Commands;

import org.example.WebScraper;

import java.util.ArrayList;

public class GetNewsCommandAction extends CommandActionAdapter {
    public GetNewsCommandAction(String commandID) {
        super(commandID);
    }

    @Override
    public ActionResponce execute() {
        ArrayList<String> newsList = WebScraper.getNewsForSpecificSymbol(getOption("symbol").getAsString(), getOption("amount").getAsInteger());
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
