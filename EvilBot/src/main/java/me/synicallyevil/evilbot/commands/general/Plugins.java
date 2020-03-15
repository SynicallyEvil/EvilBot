package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.evilbot.plugins.Plugin;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class Plugins implements Command {

    private boolean enabled = true;

    @Override
    public String getCommand() {
        return "plugins";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public void onCommand(String[] strings, CommandHandler commandHandler) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Plugin List:").setDescription(" ").setColor(Color.GREEN);

        StringBuilder sBuilder = new StringBuilder();
        int i = 1;
        for(Plugin plugin : commandHandler.getEvilBot().getPlugins()){
            sBuilder.append(String.format("%s: %s [%s] created by: %s", i, plugin.getName(), plugin.getVersion(), plugin.getAuthor())).append("\r\n");
            i++;
        }

        builder.setDescription(sBuilder.toString());
        commandHandler.sendMessage(builder);
    }
}
