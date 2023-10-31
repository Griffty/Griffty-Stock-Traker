package org.example.DiscordRequestHandlers.Buttons;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.HelpCommandAction;

public class HelpButtonAction extends ButtonCommandActionAdapter{
    public HelpButtonAction(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = new HelpCommandAction("?");
        return ActionResponce.SUCCESS;
    }
}
