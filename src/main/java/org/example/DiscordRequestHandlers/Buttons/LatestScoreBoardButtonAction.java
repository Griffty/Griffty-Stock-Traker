package org.example.DiscordRequestHandlers.Buttons;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.GetScoreboardCommandAction;

public class LatestScoreBoardButtonAction extends ButtonCommandActionAdapter{
    public LatestScoreBoardButtonAction(String actionId) {
        super(actionId);
    }
    @Override
    public ActionResponce execute() {
        successAnswer = new GetScoreboardCommandAction("?");
        return ActionResponce.SUCCESS;
    }
}
