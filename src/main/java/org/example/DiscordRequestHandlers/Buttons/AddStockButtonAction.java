package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.example.DiscordRequestHandlers.Commands.ActionResponce;

import java.util.List;

public class AddStockButtonAction extends ButtonModalActionAdapter{
    public AddStockButtonAction(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = createBasicModal("addSubscribedStocksM", "Subscribe to stock", List.of(
                createBasicTextInput("symbol", "Symbol", TextInputStyle.SHORT, "AMZN, AAPL, F, etc.", true)
        ));
        return ActionResponce.SUCCESS;
    }
}
