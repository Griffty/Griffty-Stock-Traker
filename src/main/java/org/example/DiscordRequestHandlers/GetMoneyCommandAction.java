package org.example.DiscordRequestHandlers;

public class GetMoneyCommandAction extends CommandAdapterAction {
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
