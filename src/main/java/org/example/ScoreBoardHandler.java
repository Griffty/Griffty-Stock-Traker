package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.example.users.BotUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TimerTask;

public class ScoreBoardHandler extends TimerTask {
    private static ScoreBoardHandler self;
    public static ScoreBoardHandler getInstance() {
        if (self == null){
            self = new ScoreBoardHandler();
        }
        return self;
    }
    private ScoreBoardHandler(){
        SchedulerUtil.scheduleDayleTaskAt(2, 0, this);
    }
    private MessageEmbed scoreEmbed;
    @Override
    public void run() {
        System.out.println("Starting to make scoreboard");
        int maxItems = 100;
        long t = System.currentTimeMillis();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<BotUser> allUsers = FileHandler.getInstance().deserializeAll();
        allUsers.sort(Comparator.comparingDouble(BotUser::getTotalMoney));
        for (int i = 0; i < Math.min(allUsers.size(), maxItems); i++){
            stringBuilder.append(i).append(". ").append(allUsers.get(i).name).append(" with net worth of ")
                    .append(allUsers.get(i).getTotalMoney()).append("$.\n");
        }
        embedBuilder.setTitle("Score Board");
        embedBuilder.setDescription(stringBuilder.toString());
        scoreEmbed = embedBuilder.build();
        System.out.println("Time to build score board: " + (System.currentTimeMillis() - t)/1000 + " seconds");
    }

    public MessageEmbed getScoreEmbed() {
        if (scoreEmbed == null){
            return new EmbedBuilder().setTitle("Score board wasn't created yet.").build();
        }
        return scoreEmbed;
    }
}
