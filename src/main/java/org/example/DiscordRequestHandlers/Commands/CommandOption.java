package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class CommandOption {
    private final String optionID;
    private final String description;
    private final OptionType optionType;
    private final boolean required;
    private final boolean autoComplete;

    public CommandOption(OptionType optionType, String optionID, String description,  boolean required, boolean autoComplete) {
        this.optionID = optionID;
        this.description = description;
        this.required = required;
        this.autoComplete = autoComplete;
        this.optionType = optionType;
    }
    public CommandOption(OptionType optionType, String optionID, String description) {
        this.optionID = optionID;
        this.description = description;
        this.required = false;
        this.autoComplete = false;
        this.optionType = optionType;
    }

    public String getOptionID() {
        return optionID;
    }

    public String getDescription() {
        return description;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }
}
