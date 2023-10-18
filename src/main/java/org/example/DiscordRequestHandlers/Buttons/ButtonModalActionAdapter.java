package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.ActionResponce;

public abstract class ButtonModalActionAdapter extends AbstractButtonModalAction{

    protected Modal successAnswer;
    protected String failAnswer;
    protected String noActionAnswer;
    protected ButtonModalActionAdapter(String actionId) {
        super(actionId);
    }

    @Override
    protected RestAction finish(ActionResponce actionResponce) {
        switch (actionResponce){
            case SUCCESS -> {
                return getEvent().replyModal(successAnswer);
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
