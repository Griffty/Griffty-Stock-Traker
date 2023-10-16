package org.example.DiscordRequestHandlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.SensitiveInformation;
import org.example.Stock;

import java.awt.*;
import java.util.List;

public class MenuCommand extends AbstractResponseCommand{
    private EmbedBuilder builder;
    private List<ItemComponent> addedComponents;
    public MenuCommand(String commandId) {
        super(commandId);
        isEphemeral = true;
    }

    @Override
    public CommandResponse execute() {
        StringBuilder sS = new StringBuilder();
        if (user.subscribedStocks.isEmpty()){
            sS.append("You are not subscribed to any stock.");
        }else {
            for (String symbol : user.subscribedStocks) {
                if (user.subscribedStocks.get(user.subscribedStocks.size() - 1).equals(symbol)) {
                    sS.append(symbol).append(".");
                    continue;
                }
                sS.append(symbol).append(", ");
            }
        }

        StringBuilder sO = new StringBuilder();
        if (user.getStocksInProperty().isEmpty()){
            sO.append("You are not owning any stocks.");
        }else {
            for (Stock stock : user.getStocksInProperty()) {
                sO.append(stock.getSymbol()).append(" - ").append(stock.getAmount()).append("; ");
            }
        }
        builder = new EmbedBuilder()
                .setTitle("User menu")
                .setThumbnail("https://repository-images.githubusercontent.com/156847937/2ac66980-0f3d-11eb-8e62-693087aa1f67")
                .setDescription(SensitiveInformation.menuDescription)
                .setColor(Color.red)
                .addField("Subscribed Stocks:", sS.toString(), true)
                .addField("Balance:", String.format("%.2f", user.getMoney()).replace(",", ".") +"$", true)
                .addField("Subscribed Stocks:", sO.toString(), false);

//        Button buyButton = Button.primary(getBotUser().discordId+"_BuysStock", "Buy");
//        Button sellButton = Button.danger(getBotUser().discordId+"_SellStock", "Sell");
//        addedComponents.add(buyButton);
//        addedComponents.add(sellButton);
        return CommandResponse.SUCCESS;
    }

    @Override
    protected RestAction<Message> finish(CommandResponse commandResponse) {
        return getInteractionHook().sendMessageEmbeds(builder.build());
    }

    @Override
    protected void clear() {

    }
}
