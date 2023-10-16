package org.example.DiscordRequestHandlers;

import org.example.WebScraper;

import java.util.ArrayList;

public class GetNewsCommand extends ResponseCommandAdapter {
    public GetNewsCommand(String commandID) {
        super(commandID);
    }

    @Override
    public CommandResponse execute() {
        ArrayList<String> newsList = WebScraper.getNewsForSpecificSymbol(options.get(0).getValue().getAsString(), options.get(1).getValue().getAsInt());
        StringBuilder s = new StringBuilder();
        for (String article : newsList){
            s.append(article).append(",\n");
        }
        successAnswer = s.toString();
        failAnswer = s.toString();
        if (newsList.get(0).contains("Cannot")){
            return CommandResponse.FAIL;
        }
        return CommandResponse.SUCCESS;
    }
}
