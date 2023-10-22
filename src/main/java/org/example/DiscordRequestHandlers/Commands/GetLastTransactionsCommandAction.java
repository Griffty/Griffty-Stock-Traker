package org.example.DiscordRequestHandlers.Commands;

import org.example.TransactionLogger;

import java.util.ArrayList;

public class GetLastTransactionsCommandAction extends CommandActionAdapter{

    public GetLastTransactionsCommandAction(String commandId) {
        super(commandId);
        setEphemeral(true);
    }

    @Override
    public ActionResponce execute() {
        ArrayList<String> transactions = TransactionLogger.getInstance().getLatestTransactionLogs(getBotUser());
        if (transactions.isEmpty()){
            noActionAnswer = "You didn't made any transactions yet";
            return ActionResponce.NO_ACTION;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : transactions){
            stringBuilder.append("\n").append(string);
        }
        successAnswer = stringBuilder.toString();
        return ActionResponce.SUCCESS;
    }
}
