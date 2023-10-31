package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Commands.AbstractCommandAction;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

public abstract class ModalCommandActionAdapter extends AbstractModalAction{
    protected AbstractCommandAction successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    protected ModalCommandActionAdapter(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    protected RestAction finish(ActionResponce actionResponce) {
        switch (actionResponce){
            case SUCCESS -> {
                return successAnswer.build(getBotUser(), getInteractionHook());
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
