package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import org.example.JsonSaveHandler;
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
        System.out.println("here");
        event.reply("").setEphemeral(true).queue(); // we can use this as empty reply

        List<String> idSplit = List.of(event.getButton().getId().split("_"));
        AbstractButtonAction action = getButtonAction(idSplit.get(1));
        if (action == null){
            event.reply("Cannot find specified command").queue();
            return;
        }
        if (delayIfUserInSystem(event.getUser().getId())){
            event.reply("Time Out Error, try again in few seconds").setEphemeral(true).queue();
            return;
        }
        BotUser user = getBotUser(event.getUser());
        if (user == null){
            event.getHook().sendMessage("You are not registered. Type /registered to fix it.").setEphemeral(true).queue();
            return;
        }
        action.build(user, event.getHook());
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
        System.out.println("here");
        for (int i = 0; i < components.size(); i++){
            if (i % 5 == 0 && i != 0){
                System.out.println(row);
                messageCreateAction.addActionRow(row);
                row.clear();
            }
            if (components.get(i) == null){
                continue;
            }
            System.out.println("add");
            row.add(components.get(i));
            if (i == components.size()-1 && i % 5 != 0){
                messageCreateAction.addActionRow(row);
            }
        }
        return messageCreateAction;
    }
}