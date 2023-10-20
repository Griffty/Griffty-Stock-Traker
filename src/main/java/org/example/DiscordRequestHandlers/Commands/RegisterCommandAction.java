package org.example.DiscordRequestHandlers.Commands;

import org.example.JsonSaveHandler;
import org.example.users.BotUser;

public class RegisterCommandAction extends CommandActionAdapter {
    public RegisterCommandAction(String commandId) {
        super(commandId);
    }
    @Override
    public ActionResponce execute() {
        setUser(new BotUser(getInteractionHook().getInteraction().getUser().getId(), getInteractionHook().getInteraction().getUser().getName()));
        int answer = JsonSaveHandler.getInstance().registerNewUser(getBotUser());
        if (answer == 1){
            successAnswer = "Hooray, you just registered.";
            return ActionResponce.SUCCESS;
        }
        if (answer == 0){
            failAnswer = "Something went wrong, contact developers for more information.";
            return ActionResponce.NO_ACTION;
        }
        if (answer < 0){
            noActionAnswer = "Everything cool, you are already in system.";
            return ActionResponce.FAIL;
        }
        return null;
    }
}
