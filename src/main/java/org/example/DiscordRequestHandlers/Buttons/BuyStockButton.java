package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.DiscordRequestHandlers.ActionResponce;

public class BuyStockButton extends AbstractButtonAction {
    protected BuyStockButton(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {

        return ActionResponce.SUCCESS;
    }

    @Override
    protected RestAction<Message> finish(ActionResponce actionResponce) {
        return getInteractionHook().sendMessage("");
    }

    @Override
    protected void clear() {

    }
}
