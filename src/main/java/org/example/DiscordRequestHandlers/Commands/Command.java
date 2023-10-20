package org.example.DiscordRequestHandlers.Commands;

public abstract class Command {
    private final String commandId;
    protected Command(String commandId) {
        this.commandId = commandId;
    }

    public String getCommandId() {
        return commandId;
    }
}