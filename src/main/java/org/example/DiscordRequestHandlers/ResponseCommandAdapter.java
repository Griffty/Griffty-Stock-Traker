package org.example.DiscordRequestHandlers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;

public abstract class ResponseCommandAdapter extends AbstractResponseCommand{
    protected String successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    public ResponseCommandAdapter(String commandId) {
        super(commandId);
    }

    @Override
    protected RestAction<Message> finish(CommandResponse commandResponse) {
        switch (commandResponse){
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
