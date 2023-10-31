package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import org.example.FileHandler;
import org.example.users.BotUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ButtonActionHandler extends ListenerAdapter { // I can make it as a UniversalActionHandler <T ? extends DiscordAction>
    private static ButtonActionHandler self;
    private final HashSet<AbstractButtonAction> abstractButtonActions = new HashSet<>();
    public static ButtonActionHandler getInstance() {
        if (self == null){
            self = new ButtonActionHandler();
        }
        return self;
    }
    public ButtonActionHandler registerNewButtonAction(AbstractButtonAction action){
        abstractButtonActions.add(action);
        return this;
    }
    private ButtonActionHandler(){}

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
         // we can use this as empty reply
        List<String> idSplit = List.of(event.getButton().getId().split("_"));

        if (!idSplit.get(0).equals("?")){
            event.reply("Actions with specified user aren't currently supported").queue();
        }
        AbstractButtonAction action = getButtonAction(idSplit.get(1));

        if (action == null){
            event.reply("Cannot find specified command").queue();
            return;
        }
        if (!(action instanceof AbstractButtonModalAction)){
            event.deferReply().queue();
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
        while (isUserInSystem(name) && sleepCycle < 20){
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
    private AbstractButtonAction getButtonAction(String actionId) {
        for (AbstractButtonAction action : abstractButtonActions) {
            if (action.getActionId().equals(actionId)){
                return action.createCopy();
            }
        }
        return null;
    }
    public static RestAction<Message> buildMessageWithActionRows(WebhookMessageCreateAction<Message> messageCreateAction, List<ItemComponent> components){
        List<ItemComponent> row = new ArrayList<>();
        for (int i = 0; i < components.size(); i++){
            if (i % 5 == 0 && i != 0){
                messageCreateAction.addActionRow(row);
                row.clear();
            }
            if (components.get(i) == null){
                continue;
            }
            row.add(components.get(i));
            if (i == components.size()-1 && i % 5 != 0){
                messageCreateAction.addActionRow(row);
            }
        }
        return messageCreateAction;
    }
}