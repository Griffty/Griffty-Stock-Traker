package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.users.BotUser;

public abstract class AbstractButtonModalAction extends AbstractButtonAction{
    protected AbstractButtonModalAction(String actionId) {
        super(actionId);
    }

    @Override
    public void build(BotUser user, ButtonInteractionEvent event){
        this.setUser(user);
        this.setEvent(event);

        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            event.reply(getBotUser() + " Sorry but I can handle this button, contact developers for fix").queue();
            return;
        }
        RestAction<Message> response = finish(actionResponce);
        if (response == null){
            //SaveError;
            event.reply(getBotUser() + " Sorry but something unexpected happened, contact developers for fix").queue();
            return;
        }
        response.queue();
        serialize();
        clear();
    }
}
