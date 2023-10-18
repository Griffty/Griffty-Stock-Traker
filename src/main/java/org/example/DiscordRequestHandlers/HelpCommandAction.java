package org.example.DiscordRequestHandlers;

import org.example.SensitiveInformation;

public class HelpCommandAction extends CommandActionAdapter {
    public HelpCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = SensitiveInformation.helpInfo;
        return ActionResponce.SUCCESS;
    }
}
