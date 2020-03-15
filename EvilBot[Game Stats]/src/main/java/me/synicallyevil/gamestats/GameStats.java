package me.synicallyevil.gamestats;

import me.synicallyevil.evilbot.EvilBotPlugin;

public class GameStats implements EvilBotPlugin {

    @Override
    public String pluginName() {
        return "Game Statistics";
    }

    @Override
    public String pluginVersion() {
        return "1.0";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {

    }

    public static void main(String[] args){
        System.out.println("Hello World.");
    }
}
