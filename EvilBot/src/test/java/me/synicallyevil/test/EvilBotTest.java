package me.synicallyevil.test;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotAPI;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.test.commands.HelloWorld;
import me.synicallyevil.test.listeners.MessageListener;

public class EvilBotTest implements EvilBotPlugin {

    @Override
    public String pluginName() {
        return "HelloWorld";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public String pluginVersion() {
        return "1.0";
    }

    @Override
    public void onLoad(){
        EvilBotAPI api = EvilBot.getBot().getApi();

        api.addCommand(new HelloWorld());
        api.addListener(new MessageListener());
    }
}
