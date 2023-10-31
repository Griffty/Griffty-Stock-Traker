package org.example.DiscordRequestHandlers.Commands;

public class SetEphemeralMessages extends CommandActionAdapter {
    public SetEphemeralMessages(String commandId) {
        super(commandId);
    }
    @Override
    public ActionResponce execute() {
        boolean b = getOption("state").getAsBoolean();
        getBotUser().setEphemeralMessages(b);
        successAnswer = "Now your messages will be visible " + (b ? "only to you" : "to everyone.");
        return ActionResponce.SUCCESS;
    }
}
