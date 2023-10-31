package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.BuyStockCommandAction;
import org.example.DiscordRequestHandlers.Commands.OptionAnswer;
import org.example.DiscordRequestHandlers.Commands.SellStockCommandAction;
import org.example.users.Stock;
import org.example.TradeHandler;

import java.util.List;

public class BuyStockModalAction extends ModalCommandActionAdapter{

    public BuyStockModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();
        String amount = getOption("amount").getAsString();

        successAnswer = new BuyStockCommandAction("?")
                .addOptionAnswers(new OptionAnswer(OptionType.STRING, "symbol", symbol))
                .addOptionAnswers(new OptionAnswer(OptionType.INTEGER, "amount", amount));
        return ActionResponce.SUCCESS;
    }
}
