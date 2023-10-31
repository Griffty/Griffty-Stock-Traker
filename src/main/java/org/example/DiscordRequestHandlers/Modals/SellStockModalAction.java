package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.interactions.command.SlashCommandInteractionImpl;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.OptionAnswer;
import org.example.DiscordRequestHandlers.Commands.SellStockCommandAction;

import java.util.List;

public class SellStockModalAction extends ModalCommandActionAdapter{
    public SellStockModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();
        String amount = getOption("amount").getAsString();

        successAnswer = new SellStockCommandAction("?")
                .addOptionAnswers(new OptionAnswer(OptionType.STRING, "symbol", symbol))
                .addOptionAnswers(new OptionAnswer(OptionType.INTEGER, "amount", amount));
        return ActionResponce.SUCCESS;
    }
}
