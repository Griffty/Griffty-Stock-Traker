package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.example.DiscordRequestHandlers.Commands.OptionAnswer;
import org.example.FileHandler;
import org.example.users.BotUser;

import java.util.HashSet;
import java.util.Objects;

public class ModalActionHandler extends ListenerAdapter {
    private static ModalActionHandler self;
    private final HashSet<AbstractModalAction> abstractModalActions = new HashSet<>();
    public static ModalActionHandler getInstance() {
        if (self == null){
            self = new ModalActionHandler();
        }
        return self;
    }
    private ModalActionHandler(){}

    public ModalActionHandler registerNewModalAction(AbstractModalAction modalAction) {
        abstractModalActions.add(modalAction);
        return this;
    }
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        AbstractModalAction action = getModalAction(event);
        if (action == null){
            event.reply("Cannot find specified modal").queue();
            return;
        }

        if (delayIfUserInSystem(event.getUser().getName())){
            event.reply("Time Out Error, try again in few seconds").setEphemeral(true).queue();
            return;
        }
        BotUser user = getBotUser(event.getUser());
        if (user == null){
            event.getHook().sendMessage("You are not registered. Type /registered to fix it.").setEphemeral(true).queue();
            return;
        }

        action.build(user, event);

    }

    private boolean delayIfUserInSystem(String name) {
        int sleepCycle = 0;
        while (isUserInSystem(name) && sleepCycle < 40){ // for optimization can add is user registered true, bcs if he is deserialized he must be registered.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sleepCycle++ ;
        }

        return sleepCycle == 40;
    }

    private boolean isUserInSystem(String name) {
        return FileHandler.getInstance().isUserInSystem(name);
    }

    private BotUser getBotUser(User user) {
        return FileHandler.getInstance().deserializeUser(user.getName(), user.getId());
    }

    private AbstractModalAction getModalAction(ModalInteractionEvent event) {
        AbstractModalAction responseAction = null;
        String modalId = event.getModalId().split("_")[1];
        for(AbstractModalAction action : abstractModalActions) {
            if (action.getActionId().equals(modalId)) {
                responseAction = action.createCopy();
                break;
            }
        }
        if (responseAction == null){
            return null;
        }
        if (responseAction.getRequiredOptions().isEmpty()){
            return responseAction;
        }
        for(String option : responseAction.getRequiredOptions()){
            String answer = Objects.requireNonNull(event.getValue(option)).getAsString();
            responseAction.addOptionAnswer(new OptionAnswer(OptionType.STRING, option, answer));
        }

        return responseAction;
    }
}
