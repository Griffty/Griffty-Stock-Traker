package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.JsonSaveHandler;
import org.example.ServerInterface;
import org.example.users.BotUser;

import java.util.HashSet;
import java.util.List;

public class CommandActionHandler extends ListenerAdapter {
    private static CommandActionHandler self;
    private final HashSet<AbstractCommandAction> abstractCommandActions = new HashSet<>();
    public static CommandActionHandler getInstance() {
        if (self == null){
            self = new CommandActionHandler();
        }
        return self;
    }
    private CommandActionHandler(){}
    public CommandActionHandler registerNewCommand(AbstractCommandAction command){
        abstractCommandActions.add(command);
        return this;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        AbstractCommandAction command = getCommandAction(event);
        if (command == null){
            event.reply("Cannot find specified command").queue();
            return;
        }

        if (delayIfUserInSystem(event.getUser().getId())){
            event.reply("Time Out Error, try again in few seconds").setEphemeral(true).queue();
            return;
        }
        event.deferReply(command.isEphemeral()).queue();
        if (command instanceof RegisterCommandAction){
            command.build(null, event.getHook());
            return;
        }

        BotUser user = getBotUser(event.getUser());
        if (user == null){
            event.getHook().sendMessage("You are not registered. Type /registered to fix it.").setEphemeral(true).queue();
            return;
        }

        command.build(user, event.getHook());
    }

    private boolean delayIfUserInSystem(String id) {
        int sleepCycle = 0;
        while (isUserInSystem(id) && sleepCycle < 40){ // for optimization can add is user registered true, bcs if he is deserialized he must be registered.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sleepCycle++ ;
        }

        return sleepCycle == 40;
    }

    private boolean isUserInSystem(String id) {
        return JsonSaveHandler.getInstance().isUserInSystem(id);
    }

    private BotUser getBotUser(User user) {
        return JsonSaveHandler.getInstance().deserializeUser(user.getName(), user.getId());
    }

    private AbstractCommandAction getCommandAction(SlashCommandInteractionEvent event) {
        AbstractCommandAction responseCommand = null;
        List<CommandOption> options = null;
        for(AbstractCommandAction command : abstractCommandActions){
            if (command.getCommandId().equals(event.getName())){
                responseCommand = command.createCopy();
                if (responseCommand == null){
                    throw new RuntimeException("WTF2??? " + command.getCommandId());
                }
                options = ServerInterface.getInstance().getOptionsForCommand(event.getName());
            }
        }
        if (responseCommand != null){
            if (options != null){
                for (CommandOption option : options) {
                    responseCommand.addOptionAnswers(new CommandOptionAnswer(option.getOptionType(), option.getOptionID(), event.getOption(option.getOptionID())));
                }
            }
            return responseCommand;
        }
        return null;
    }
}
