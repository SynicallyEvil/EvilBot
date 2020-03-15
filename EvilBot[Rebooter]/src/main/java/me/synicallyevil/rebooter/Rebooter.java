package me.synicallyevil.rebooter;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.rebooter.commands.Restart;
import net.dv8tion.jda.api.entities.User;

import java.io.File;

public class Rebooter implements EvilBotPlugin {

    @Override
    public String pluginName() {
        return "Rebooter";
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
        Runtime.getRuntime().addShutdownHook(new Thread(() -> quit()));
        EvilBot.getBot().getApi().addCommand(new Restart());

        File file = new File(System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "rebooted.txt");
        if(file.exists()){
            file.delete();

            sendMessage("I have rebooted!");
        }
    }

    private void quit(){
        sendMessage("I have been stopped!");
    }

    private void sendMessage(String message){
        new java.util.Timer().schedule(new java.util.TimerTask() {

            @Override
            public void run() {
                User me = EvilBot.getBot().getJDA().getUserById("338881519198404609");
                me.openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
            }

        }, 2500);
    }
}
