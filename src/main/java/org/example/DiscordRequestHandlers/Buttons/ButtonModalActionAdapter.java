package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

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
    protected Modal createBasicModal(String customId, String label, List<TextInput> inputs){
        Modal.Builder modal = Modal.create("?_"+customId, label);
        for (TextInput input : inputs){
            modal.addActionRow(input);
        }
        return modal.build();
    }
    protected TextInput createBasicTextInput(String id, String label, TextInputStyle style, String placeHolder, boolean required){
        return TextInput.create(id, label, style).setPlaceholder(placeHolder).setRequired(required).build();
    }

    @Override
    protected void clear() {

    }
}
