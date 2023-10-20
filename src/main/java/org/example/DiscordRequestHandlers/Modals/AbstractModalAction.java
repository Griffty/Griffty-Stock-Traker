package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.JsonSaveHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModalAction{
    private final String actionId;  //format {userDiscordID_actionId}
    private BotUser user;
    private ModalInteractionEvent event;
    protected ActionResponce actionResponce;
    private final List<String> requiredOptions;
    private final List<ModalOptionAnswer> option = new ArrayList<>();

    protected AbstractModalAction(String actionId, List<String> requiredOptions) {
        this.actionId = actionId;
        this.requiredOptions = requiredOptions;
    }

    public void build(BotUser user, ModalInteractionEvent event){
        System.out.println("buildingModal");
        this.user = user;
        this.event = event;
        event.deferReply().queue();
        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            getInteractionHook().sendMessage(getBotUser() + " Sorry but I can handle this button, contact developers for fix").queue();
            return;
        }
        RestAction<Message> response = finish(actionResponce);
        if (response == null){
            //SaveError;
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
    protected ModalInteractionEvent getEvent(){
        return event;
    }
    protected List<String> getRequiredOptions() {
        return requiredOptions;
    }
    protected void setUser(BotUser user) {
        this.user = user;
    }

    public void setEvent(ModalInteractionEvent event) {
        this.event = event;
    }
    protected String pingUser(){
        return "<@" + getBotUser().discordId + "> ";
    }
    protected String getOption(String optionId){
        for (ModalOptionAnswer answer : option){
            if (optionId.equals(answer.optionID())){
                return answer.value();
            }
        }
        return null;
    }

    public AbstractModalAction createCopy() {
        try {
            Constructor<? extends AbstractModalAction> constructor = this.getClass().getConstructor(String.class, List.class);
            return constructor.newInstance(this.getActionId(), this.getRequiredOptions());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a copy", e);
        }
    }

    public void addOptionAnswer(ModalOptionAnswer modalOptionAnswer) {
        option.add(modalOptionAnswer);
    }
}
