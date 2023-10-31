package org.example.DiscordRequestHandlers.Commands;

import org.example.TransactionLogger;

import java.util.ArrayList;

public class GetTransactionsCommandAction extends CommandActionAdapter{

    public GetTransactionsCommandAction(String commandId) {
        super(commandId);
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
