package org.example.DiscordRequestHandlers.Buttons;

import net.dv8tion.jda.api.interactions.InteractionHook;
import org.example.users.BotUser;

public abstract class AbstractButtonAction {
    String actionID;
    BotUser user;
    InteractionHook hook;
}
