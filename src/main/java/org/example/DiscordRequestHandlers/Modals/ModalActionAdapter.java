package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.ActionResponce;

import java.util.List;

public abstract class ModalActionAdapter extends AbstractModalAction{
    protected String successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    protected ModalActionAdapter(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    protected RestAction finish(ActionResponce actionResponce) {
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
