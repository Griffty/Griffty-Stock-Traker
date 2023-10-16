package org.example.DiscordRequestHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlashCommandStructure extends Command{
    private final String description;
    private final List<CommandOption> listOfOptions;

    public SlashCommandStructure(String commandId, String description, List<CommandOption> listOfOptions) {
        super(commandId);
        this.description = description;
        this.listOfOptions = Objects.requireNonNullElseGet(listOfOptions, ArrayList::new);
    }

    public String getDescription() {
        return description;
    }

    public List<CommandOption> getListOfOptions() {
        return listOfOptions;
    }
}
