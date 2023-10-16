package org.example.DiscordRequestHandlers;

public class GetMoneyCommand extends ResponseCommandAdapter{
    public GetMoneyCommand(String commandId) {
        super(commandId);
    }

    @Override
    public CommandResponse execute() {
        double money = user.getMoney();
        successAnswer = "Your current balance is " + money + "$";
        return CommandResponse.SUCCESS;
    }

}
