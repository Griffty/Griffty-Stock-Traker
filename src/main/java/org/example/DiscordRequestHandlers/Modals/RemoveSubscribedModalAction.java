package org.example.DiscordRequestHandlers.Modals;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.DiscordRequestHandlers.Commands.*;

import java.util.List;

public class RemoveSubscribedModalAction extends ModalCommandActionAdapter{
    public RemoveSubscribedModalAction(String actionId, List<String> requiredOptions) {
        super(actionId, requiredOptions);
    }

    @Override
    public ActionResponce execute() {
        String symbol = getOption("symbol").getAsString();

        successAnswer = new RemoveSubscribedStockCommandAction("?")
                .addOptionAnswers(new OptionAnswer(OptionType.STRING, "symbol", symbol));
        return ActionResponce.SUCCESS;
    }
}
