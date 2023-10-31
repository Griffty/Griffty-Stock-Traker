package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.io.ObjectStreamException;

public class OptionAnswer {
    private final Object answer;
    private final OptionType optionType;
    private final String optionID;

    public OptionAnswer(OptionType optionType, String optionID, Object optionMapping) {
        this.optionType = optionType;
        this.optionID = optionID;
        this.answer = optionMapping;
        System.out.println(answer);
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public String getOptionID() {
        return optionID;
    }

    public String getAsString(){
        return (String) answer;
    }

    public int getAsInteger(){
        return Integer.parseInt((String) answer);
    }

    public boolean getAsBoolean(){
        return (Boolean) answer;
    }

    public Object getRawValue(){
        return answer;
    }
}
