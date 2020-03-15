package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Uptime implements Command {
    private boolean enabled = true;

    @Override
    public String getCommand() {
        return "uptime";
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
        this.enabled = state;
    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        String time = "0 hours, 0 minutes, and 0 seconds.";
        long estimatedTime = handler.getEvilBot().getUptimeStarted() - System.currentTimeMillis();

        long hours = TimeUnit.MILLISECONDS.toHours(estimatedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(estimatedTime);
        long seconds = TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((estimatedTime)));

        if(hours > 0)
            time = String.format("%s hours, %s minutes, and %s seconds.", hours, minutes, seconds);
        else if(minutes > 0)
            time = String.format("%s minutes, and %s seconds.", minutes, seconds);
        else if(seconds > 0)
            time = String.format("%s seconds.", seconds);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setAuthor("Uptime");
        builder.setDescription("Current uptime: " + time);

        handler.sendMessage(builder);
    }
}
