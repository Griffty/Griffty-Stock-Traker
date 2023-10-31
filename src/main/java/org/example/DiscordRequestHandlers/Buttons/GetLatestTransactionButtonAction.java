package org.example.DiscordRequestHandlers.Buttons;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.GetTransactionsCommandAction;

public class GetLatestTransactionButtonAction extends ButtonCommandActionAdapter{
    public GetLatestTransactionButtonAction(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = new GetTransactionsCommandAction("?");
        return ActionResponce.SUCCESS;
    }

}
