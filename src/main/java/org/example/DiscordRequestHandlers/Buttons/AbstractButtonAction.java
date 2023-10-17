package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.AbstractCommandAction;
import org.example.DiscordRequestHandlers.ActionResponce;
import org.example.DiscordRequestHandlers.CommandOptionAnswer;
import org.example.JsonSaveHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractButtonAction {
    private final String actionId;  //format {userDiscordID_actionId}
    private BotUser user;
    private InteractionHook hook;
    private ActionResponce actionResponce;
    private boolean personal;

    protected AbstractButtonAction(String actionId) {
        this.actionId = actionId;
    }

    public void build(BotUser user, InteractionHook hook){
        this.user = user;
        this.hook = hook;

        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but I can handle this button, contact developers for fix").queue();
            return;
        }
        RestAction<Message> response = finish(actionResponce);
        if (response == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but something unexpected happened, contact developers for fix").queue();
            return;
        }
        response.queue();
        serialize();
        clear();
    }
    public abstract ActionResponce execute();
    protected abstract RestAction<Message> finish(ActionResponce actionResponce);
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
        return hook;
    }

    protected void setUser(BotUser user) {
        this.user = user;
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
