package org.example.DiscordRequestHandlers;

import org.example.JsonSaveHandler;
import org.example.users.BotUser;

public class RegisterCommand extends ResponseCommandAdapter {
    public RegisterCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        user = new BotUser(hook.getInteraction().getUser().getId(), hook.getInteraction().getUser().getName());
        if (JsonSaveHandler.getInstance().registerNewUser(user) == 1){
            successAnswer = "Hooray, you just registered.";
            return CommandResponse.SUCCESS;
        }
        if (JsonSaveHandler.getInstance().registerNewUser(user) == 0){
            failAnswer = "Something went wrong, contact developers for more information.";
            return CommandResponse.NO_ACTION;
        }
        if (JsonSaveHandler.getInstance().registerNewUser(user) < 0){
            noActionAnswer = "Everything cool, you are already in system.";
            return CommandResponse.FAIL;
        }
        return null;
    }
}
