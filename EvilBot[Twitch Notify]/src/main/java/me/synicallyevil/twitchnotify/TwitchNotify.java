package me.synicallyevil.twitchnotify;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotAPI;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.twitchnotify.listeners.Events;

public class TwitchNotify implements EvilBotPlugin {

    @Override
    public String pluginName() {
        return "TwitchNotify";
    }

    @Override
    public String pluginVersion() {
        return "1.0.1";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {
        EvilBotAPI api = EvilBot.getBot().getApi();

        api.addListener(new Events());
    }
}
