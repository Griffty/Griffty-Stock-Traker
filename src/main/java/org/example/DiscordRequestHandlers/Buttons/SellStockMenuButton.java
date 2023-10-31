package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

public class SellStockMenuButton extends ButtonModalActionAdapter{
    public SellStockMenuButton(String actionId) {
        super(actionId);
    }
    @Override
    public ActionResponce execute() {
        successAnswer = createBasicModal("sellStockM", "Sell Stock",
                List.of(createBasicTextInput("symbol", "Stock ticker", TextInputStyle.SHORT, "AAPL, AMZN, F, etc.", true),
                        createBasicTextInput("amount", "\"Amount you want to sell", TextInputStyle.SHORT, "10, 2, 18, etc.", true)));
        return ActionResponce.SUCCESS;
    }
}
