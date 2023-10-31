package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

public class RemoveStockButtonAction extends ButtonModalActionAdapter{
    public RemoveStockButtonAction(String actionId) {
        super(actionId);
    }
    @Override
    public ActionResponce execute() {
        successAnswer = createBasicModal("removeSubscribedStocksM", "Unsubscribe from stock", List.of(
                createBasicTextInput("symbol", "Symbol", TextInputStyle.SHORT, "AMZN, AAPL, F, etc.", true)
        ));
        return ActionResponce.SUCCESS;
    }
}
