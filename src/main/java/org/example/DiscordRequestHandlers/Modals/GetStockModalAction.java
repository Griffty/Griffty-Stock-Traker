package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.GetStockCommandAction;
import org.example.DiscordRequestHandlers.Commands.OptionAnswer;

import java.util.List;

public class GetStockModalAction extends ModalCommandActionAdapter{
    public GetStockModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();

        successAnswer = new GetStockCommandAction("?")
                .addOptionAnswers(new OptionAnswer(OptionType.STRING, "symbol", symbol));
        return ActionResponce.SUCCESS;
    }
}
