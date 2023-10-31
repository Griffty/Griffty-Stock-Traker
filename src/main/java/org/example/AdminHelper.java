package org.example;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.users.BotUser;

public class AdminHelper {
    private static String adminPass = "mXao8L5JWMAGE5tqu0Bq";

    public static void executeAdminCommand(SlashCommandInteractionEvent event) {
        String option = event.getOption("parameters").getAsString();
        String[] parameters = option.split(":");
        if (!parameters[0].equals(adminPass)){
            event.reply("Wrong admin password").setEphemeral(true).queue();
            return;
        }
        event.deferReply(true).queue();
        switch (parameters[1]){
            case "gm" -> giveMoney(parameters[2], parameters[3], event);
            case "rm" -> removeMoney(parameters[2], parameters[3], event);
        }

    }

    private static void giveMoney(String parameter, String parameter1, SlashCommandInteractionEvent event) {
        if (FileHandler.getInstance().isUserRegistered(parameter, "?")){
            BotUser user = FileHandler.getInstance().deserializeUser(parameter, "?");
            user.setMoney(user.getMoney() + Float.parseFloat(parameter1));
            event.getHook().sendMessage("Current money: " + user.getMoney()).queue();
            FileHandler.getInstance().serializeUser(user);
            return;
        }
        event.getHook().sendMessage("Cannot find user").queue();
    }
    private static void removeMoney(String parameter, String parameter1, SlashCommandInteractionEvent event) {
        if (FileHandler.getInstance().isUserRegistered(parameter, "?")){
            BotUser user = FileHandler.getInstance().deserializeUser(parameter, "?");
            user.setMoney(user.getMoney() - Float.parseFloat(parameter1));
            event.getHook().sendMessage("Current money: " + user.getMoney()).queue();
            FileHandler.getInstance().serializeUser(user);
            return;
        }
        event.getHook().sendMessage("Cannot find user").queue();
    }


}
