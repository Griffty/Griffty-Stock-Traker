package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.example.FileHandler;
import org.example.ServerInterface;
import org.example.users.BotUser;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

        if (delayIfUserInSystem(event.getUser().getName())){
            event.reply("Time Out Error, try again in few seconds").setEphemeral(true).queue();
            return;
        }

        BotUser user = getBotUser(event.getUser());
        if (user == null){
            event.deferReply().queue();
            if (command instanceof RegisterCommandAction){
                command.build(null, event.getHook()).queue();
                return;
            }
            event.getHook().sendMessage("You are not registered. Type /register to fix it.").setEphemeral(true).queue();
            return;
        }
        event.deferReply(user.isEphemeralMessages()).queue();
        command.build(user, event.getHook()).queue();
    }

    private boolean delayIfUserInSystem(String name) {
        int sleepCycle = 0;
        while (isUserInSystem(name) && sleepCycle < 20){ // for optimization can add is user registered true, bcs if he is deserialized he must be registered.
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sleepCycle++ ;
        }

        return sleepCycle == 20;
    }

    private boolean isUserInSystem(String name) {
        return FileHandler.getInstance().isUserInSystem(name);
    }

    private BotUser getBotUser(User user) {
        return FileHandler.getInstance().deserializeUser(user.getName(), user.getId());
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
                    Object object;
                    OptionMapping mapping = event.getOption(option.getOptionID());
                    switch (option.getOptionType()){
                        case STRING -> object = Objects.requireNonNull(mapping).getAsString();
                        case INTEGER -> object = Objects.requireNonNull(mapping).getAsInt();
                        case BOOLEAN -> object = Objects.requireNonNull(mapping).getAsBoolean();
                        default -> throw new RuntimeException("Cannot use this type as Option answer");
                    }
                    responseCommand.addOptionAnswers(new OptionAnswer(option.getOptionType(), option.getOptionID(), object));
                }
            }
            return responseCommand;
        }
        return null;
    }
}
