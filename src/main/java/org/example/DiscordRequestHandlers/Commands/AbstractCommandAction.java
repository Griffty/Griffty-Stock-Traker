package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.FileHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractCommandAction extends Command{
    private BotUser user;
    private InteractionHook hook;
    private ActionResponce actionResponce;
    private final List<OptionAnswer> options = new ArrayList<>();
    public AbstractCommandAction(String commandId) {
        super(commandId);
    }
    public RestAction<Message> build(BotUser user, InteractionHook hook){
        this.user = user;
        this.hook = hook;

        actionResponce = execute();
        if (actionResponce == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but I can handle this command, contact developers for fix").queue();
            return null;
        }
        RestAction<Message> response = finish(actionResponce);
        if (response == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but something unexpected happened, contact developers for fix").queue();
            return null;
        }
        serialize();
        clear();
        return response;
    }
    public abstract ActionResponce execute();
    protected abstract RestAction<Message> finish(ActionResponce actionResponce);
    protected abstract void clear();

    public AbstractCommandAction addOptionAnswers(OptionAnswer answer){
        options.add(answer);
        return this;
    }
    protected void serialize(){
        if (getCommandId().equals("register")){
            return;
        }
        if (user==null){
            System.out.println("Trying to serialize null user");
            return;
        }
        FileHandler.getInstance().serializeUser(user);
    }

    protected BotUser getBotUser() {
        return user;
    }

    protected InteractionHook getInteractionHook() {
        return hook;
    }

    protected List<OptionAnswer> getOptions() {
        return options;
    }

    protected void setUser(BotUser user) {
        this.user = user;
    }

    protected String pingUser(){
        return "<@" + getBotUser().discordId + "> ";
    }

    protected OptionAnswer getOption(String optionId){
        for (OptionAnswer answer : options){
            if (optionId.equals(answer.getOptionID())){
                return answer;
            }
        }
        return null;
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
