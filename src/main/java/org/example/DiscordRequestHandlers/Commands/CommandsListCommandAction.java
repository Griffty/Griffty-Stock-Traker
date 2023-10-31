package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Buttons.ButtonActionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandsListCommandAction extends AbstractCommandAction{
    private EmbedBuilder builder;
    private final List<ItemComponent> addedComponents;
    public CommandsListCommandAction(String commandId) {
        super(commandId);
        addedComponents = new ArrayList<>();
    }

    @Override
    public ActionResponce execute() {
        builder = new EmbedBuilder()
                .setTitle("Command List")
                .setColor(Color.red);
        Button addSubscribedStocksButton = Button.success("?"+"_"+"addSubscribedStocksCommandListB", "Add subscribed").asEnabled();
        addedComponents.add(addSubscribedStocksButton);
        Button removeSubscribedStocksButton = Button.success("?"+"_"+"removeSubscribedStocksCommandListB", "Remove subscribed").asEnabled();
        addedComponents.add(removeSubscribedStocksButton);
        addedComponents.add(null);
        addedComponents.add(null);
        addedComponents.add(null);

        Button helpButton = Button.primary("?"+"_"+"helpCommandListB", "Help").asEnabled();
        addedComponents.add(helpButton);
        Button stockPriceButton = Button.danger("?"+"_"+"getStockPriceCommandListB", "Get stock Price").asEnabled();
        addedComponents.add(stockPriceButton);
        Button newsButton = Button.success("?"+"_"+"getNewsCommandListB", "Stock News").asEnabled();
        addedComponents.add(newsButton);
        addedComponents.add(null);
        addedComponents.add(null);

        Button scoreboardButton = Button.success("?"+"_"+"showScoreboardCommandListB", "Show scoreboard").asEnabled();
        addedComponents.add(scoreboardButton);
        Button ephemeralMessages = Button.primary("?"+"_"+"setEphemeralCommandListB", "Change messages visibility").asEnabled(); //todo
        addedComponents.add(ephemeralMessages);

        System.out.println(addedComponents);
        return ActionResponce.SUCCESS;
    }

    @Override
    protected RestAction<Message> finish(ActionResponce actionResponce) {
        return ButtonActionHandler.buildMessageWithActionRows(getInteractionHook().sendMessageEmbeds(builder.build()), addedComponents);
    }

    @Override
    protected void clear() {

    }
}
