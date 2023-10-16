package org.example.DiscordRequestHandlers;

import org.example.SensitiveInformation;

public class HelpCommand extends ResponseCommandAdapter {
    public HelpCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        successAnswer = SensitiveInformation.helpInfo;
        return CommandResponse.SUCCESS;
    }
}
