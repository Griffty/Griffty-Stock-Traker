package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.JsonSaveHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;

public abstract class AbstractButtonAction {
    private final String actionId;  //format {userDiscordID_actionId}
    private BotUser user;
    private ButtonInteractionEvent event;
    protected ActionResponce actionResponce;
    private boolean personal;

    protected AbstractButtonAction(String actionId) {
        this.actionId = actionId;
    }

    public void build(BotUser user, ButtonInteractionEvent event){
        this.user = user;
        this.event = event;

        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            if (!event.isAcknowledged()){
                event.deferReply().queue();
            }
            getInteractionHook().sendMessage(getBotUser() + " Sorry but I can handle this button, contact developers for fix").queue();
            return;
        }
        RestAction<Message> response = finish(actionResponce);
        if (response == null){
            //SaveError;
            if (!event.isAcknowledged()){
                event.deferReply().queue();
            }
            getInteractionHook().sendMessage(getBotUser() + " Sorry but something unexpected happened, contact developers for fix").queue();
            return;
        }
        response.queue();
        serialize();
        clear();
    }
    public abstract ActionResponce execute();
    protected abstract RestAction finish(ActionResponce actionResponce);
    protected abstract void clear();
    protected void serialize(){
        JsonSaveHandler.getInstance().serializeUser(user);
    }

    public String getActionId() {
        return actionId;
    }

    protected BotUser getBotUser() {
        return user;
    }

    protected InteractionHook getInteractionHook() {
        return event.getHook();
    }
    protected ButtonInteractionEvent getEvent(){
        return event;
    }
    protected void setUser(BotUser user) {
        this.user = user;
    }

    public void setEvent(ButtonInteractionEvent event) {
        this.event = event;
    }
    protected String pingUser(){
        return "<@" + getBotUser().discordId + "> ";
    }

    public AbstractButtonAction createCopy() {
        try {
            Constructor<? extends AbstractButtonAction> constructor = this.getClass().getConstructor(String.class);
            return constructor.newInstance(this.getActionId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a copy", e);
        }
    }
}
