package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class Ping implements Command {

    boolean enabled = true;

    @Override
    public String getCommand() {
        return "ping";
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
    public void setEnabled(boolean state) {
        enabled = state;
    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.BLUE);
        builder.setAuthor("Pong!");
        builder.setDescription("Ping is: " + handler.getEvent().getJDA().getGatewayPing() + "ms");

        handler.sendMessage(builder);
    }
}
