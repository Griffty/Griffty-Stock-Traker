package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Commands.AbstractCommandAction;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

public abstract class ButtonCommandActionAdapter extends AbstractButtonAction{

    protected AbstractCommandAction successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    protected ButtonCommandActionAdapter(String actionId) {
        super(actionId);
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
