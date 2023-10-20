package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;

public abstract class CommandActionAdapter extends AbstractCommandAction {
    protected String successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    public CommandActionAdapter(String commandId) {
        super(commandId);
    }

    @Override
    protected RestAction<Message> finish(ActionResponce actionResponce) {
        switch (actionResponce){
            case SUCCESS -> {
                return getInteractionHook().sendMessage(pingUser() + successAnswer);
            }
            case FAIL -> {
                return getInteractionHook().sendMessage(pingUser() + failAnswer);
            }
            case NO_ACTION -> {
                return getInteractionHook().sendMessage(pingUser() + noActionAnswer);
            }
        }
        return null;
    }

    @Override
    protected void clear() {

    }
}
