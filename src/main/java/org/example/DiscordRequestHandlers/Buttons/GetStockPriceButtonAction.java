package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

public class GetStockPriceButtonAction extends ButtonModalActionAdapter{
    public GetStockPriceButtonAction(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = createBasicModal("getStockPriceM", "Get price of Stock", List.of(
                createBasicTextInput("symbol", "Symbol", TextInputStyle.SHORT, "AMZN, AAPL, F, etc.", true)
        ));
        return ActionResponce.SUCCESS;
    }
}
