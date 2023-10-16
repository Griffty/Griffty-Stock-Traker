package org.example.DiscordRequestHandlers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.JsonSaveHandler;
import org.example.users.BotUser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResponseCommand extends Command{
    protected BotUser user;
    protected InteractionHook hook;
    protected CommandResponse commandResponse;
    protected List<CommandOptionAnswer> options = new ArrayList<>();
    protected boolean isEphemeral = false;
    public AbstractResponseCommand(AbstractResponseCommand command) {
        super(command.getCommandId());
        options = command.options;
        hook = command.hook;
        user = command.user;
        isEphemeral = command.isEphemeral;
        commandResponse = command.commandResponse;
    }
    public AbstractResponseCommand(String commandId) {
        super(commandId);
    }
    public void build(BotUser user, InteractionHook hook){
        this.user = user;
        this.hook = hook;

        commandResponse = execute();
        if (commandResponse == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but I can handle this command, contact developers for fix").queue();
            return;
        }
        RestAction<Message> response = finish(commandResponse);
        if (response == null){
            //SaveError;
            hook.sendMessage(getBotUser() + " Sorry but something unexpected happened, contact developers for fix").queue();
            return;
        }
        response.queue();
        serialize();
        clear();
    }
    public abstract CommandResponse execute();
    protected abstract RestAction<Message> finish(CommandResponse commandResponse);
    /**
     * @override Need to add user serialization*/
    protected abstract void clear();
    protected void serialize(){
        JsonSaveHandler.getInstance().serializeUser(user);
    }

    public void addOptionAnswers(CommandOptionAnswer answer){
        options.add(answer);
    }
    protected BotUser getBotUser() {
        return user;
    }

    protected InteractionHook getInteractionHook() {
        return hook;
    }

    protected String pingUser(){
        return "<@" + getBotUser().discordId + "> ";
    }


    public AbstractResponseCommand createCopy() {
        try {
            Constructor<? extends AbstractResponseCommand> constructor = this.getClass().getConstructor(String.class);
            return constructor.newInstance(this.getCommandId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a copy", e);
        }
    }

}
