package org.example.DiscordRequestHandlers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.JsonSaveHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandAction extends Command{
    private BotUser user;
    private InteractionHook hook;
    private ActionResponce actionResponce;
    private final List<CommandOptionAnswer> options = new ArrayList<>();
    private boolean ephemeral = false;
    public AbstractCommandAction(String commandId) {
        super(commandId);
    }
    public void build(BotUser user, InteractionHook hook){
        this.user = user;
        this.hook = hook;

        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but I can handle this command, contact developers for fix").queue();
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
    /**
     * @override Need to add user serialization*/
    protected abstract void clear();

    public void addOptionAnswers(CommandOptionAnswer answer){
        options.add(answer);
    }
    protected void serialize(){
        JsonSaveHandler.getInstance().serializeUser(user);
    }

    protected boolean isEphemeral() {
        return ephemeral;
    }

    protected BotUser getBotUser() {
        return user;
    }

    protected InteractionHook getInteractionHook() {
        return hook;
    }

    protected List<CommandOptionAnswer> getOptions() {
        return options;
    }

    protected void setUser(BotUser user) {
        this.user = user;
    }

    protected void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    protected String pingUser(){
        return "<@" + getBotUser().discordId + "> ";
    }



    public AbstractCommandAction createCopy() {
        try {
            Constructor<? extends AbstractCommandAction> constructor = this.getClass().getConstructor(String.class);
            return constructor.newInstance(this.getCommandId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a copy", e);
        }
    }
}
