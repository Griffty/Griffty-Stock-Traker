package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.Buttons.ButtonActionHandler;
import org.example.SensitiveInformation;
import org.example.Stock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MenuCommandAction extends AbstractCommandAction {
    private EmbedBuilder builder;
    private final List<ItemComponent> addedComponents;
    public MenuCommandAction(String commandId) {
        super(commandId);
        setEphemeral(true);
        addedComponents = new ArrayList<>();
    }

    @Override
    public ActionResponce execute() {
        StringBuilder sS = new StringBuilder();
        if (getBotUser().subscribedStocks.isEmpty()){
            sS.append("You are not subscribed to any stock.");
        }else {
            for (String symbol : getBotUser().subscribedStocks) {
                if (getBotUser().subscribedStocks.get(getBotUser().subscribedStocks.size() - 1).equals(symbol)) {
                    sS.append(symbol).append(".");
                    continue;
                }
                sS.append(symbol).append(", ");
            }
        }

        StringBuilder sO = new StringBuilder();
        if (getBotUser().getStocksInProperty().isEmpty()){
            sO.append("You are not owning any stocks.");
        }else {
            for (Stock stock : getBotUser().getStocksInProperty()) {
                sO.append(stock.getSymbol()).append(" - ").append(stock.getAmount()).append("; ");
            }
        }
        builder = new EmbedBuilder()
                .setTitle("User menu")
                .setThumbnail("https://repository-images.githubusercontent.com/156847937/2ac66980-0f3d-11eb-8e62-693087aa1f67")
                .setDescription(SensitiveInformation.menuDescription)
                .setColor(Color.red)
                .addField("Subscribed Stocks:", sS.toString(), true)
                .addField("Balance:", String.format("%.2f", getBotUser().getMoney()).replace(",", ".") +"$", true)
                .addField("Subscribed Stocks:", sO.toString(), false);

        Button buyButton = Button.primary("?"+"_"+"buyStockMenuB", "Buy").asEnabled();
        Button sellButton = Button.danger("?"+"_"+"sellStockMenuB", "Sell").asEnabled();
        addedComponents.add(buyButton);
        addedComponents.add(sellButton);
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
