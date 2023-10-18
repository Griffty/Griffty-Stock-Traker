package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.example.DiscordRequestHandlers.ActionResponce;

public class SellStockMenuButton extends ButtonModalActionAdapter{
    public SellStockMenuButton(String actionId) {
        super(actionId);
    }
    @Override
    public ActionResponce execute() {
        TextInput symbolTextInput = TextInput.create("symbol", "Stock ticker", TextInputStyle.SHORT).setRequired(true).setPlaceholder("AAPL, AMZN, F, etc.").build();
        TextInput amountTextInput = TextInput.create("amount", "Amount you want to sell", TextInputStyle.SHORT).setRequired(true).setPlaceholder("10, 2, 18, etc.").build();

        successAnswer = Modal.create("?_sellStockM", "Sell Stock").addComponents(ActionRow.of(symbolTextInput), ActionRow.of(amountTextInput)).build();
        return ActionResponce.SUCCESS;
    }
}