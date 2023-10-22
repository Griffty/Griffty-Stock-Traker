package org.example.DiscordRequestHandlers.Buttons;

import org.example.DiscordRequestHandlers.Commands.ActionResponce;
import org.example.DiscordRequestHandlers.Commands.CommandActionHandler;
import org.example.DiscordRequestHandlers.Commands.GetLastTransactionsCommandAction;

public class GetLatestTransactionButtonAction extends ButtonCommandActionAdapter{
    public GetLatestTransactionButtonAction(String actionId) {
        super(actionId);
    }

    @Override
    public ActionResponce execute() {
        successAnswer = new GetLastTransactionsCommandAction("?");
        return ActionResponce.SUCCESS;
    }

}
