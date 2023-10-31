package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.GetNewsCommandAction;
import org.example.DiscordRequestHandlers.Commands.OptionAnswer;

import java.util.List;

public class GetNewsModalAction extends ModalCommandActionAdapter{
    public GetNewsModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();
        int amount = getOption("amount").getAsInteger();

        successAnswer = new GetNewsCommandAction("?")
                .addOptionAnswers(new OptionAnswer(OptionType.STRING, "symbol", symbol))
                .addOptionAnswers(new OptionAnswer(OptionType.INTEGER, "amount", amount));
        return ActionResponce.SUCCESS;
    }
}
