package org.example.DiscordRequestHandlers.Commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.RestAction;
import org.example.ScoreBoardHandler;

public class GetScoreboardCommandAction extends AbstractCommandAction{
    public GetScoreboardCommandAction(String commandId) {
        super(commandId);
    }
    private MessageEmbed embed;
    @Override
    public ActionResponce execute() {
        System.out.println("here");
        embed = ScoreBoardHandler.getInstance().getScoreEmbed();
        return ActionResponce.SUCCESS;
    }

    @Override
    protected RestAction<Message> finish(ActionResponce actionResponce) {
        return getInteractionHook().sendMessageEmbeds(embed);
    }

    @Override
    protected void clear() {

    }
}
