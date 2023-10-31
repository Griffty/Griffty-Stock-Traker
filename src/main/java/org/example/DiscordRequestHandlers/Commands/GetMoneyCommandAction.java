package org.example.DiscordRequestHandlers.Commands;

public class GetMoneyCommandAction extends CommandActionAdapter {
    public GetMoneyCommandAction(String commandId) {
        super(commandId);
    }

    @Override
    public ActionResponce execute() {
        double money = getBotUser().getMoney();
        successAnswer = "Your current balance is " + money + "$";
        return ActionResponce.SUCCESS;
    }

}
