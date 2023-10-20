package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class CommandOptionAnswer {
    private final OptionMapping answer;
    final OptionType optionType;
    final String optionID;

    public CommandOptionAnswer(OptionType optionType, String optionID, OptionMapping optionMapping) {
        this.optionType = optionType;
        this.optionID = optionID;
        this.answer = optionMapping;
    }

    public OptionMapping getValue(){
        return answer;
    }
}
