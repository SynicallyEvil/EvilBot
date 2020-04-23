package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
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

        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long millis = rb.getUptime();
        //long estimatedTime = handler.getEvilBot().getUptimeStarted() - System.currentTimeMillis();

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if(hours > 0)
            time = String.format("%s hour" + (hours == 1 ? "" : "s") + ", %s minute" + (minutes == 1 ? "" : "s") + ", and %s second" + (seconds == 1 ? "" : "s") + ".", hours, minutes, seconds);
        else if(minutes > 0)
            time = String.format("%s minute" + (minutes == 1 ? "" : "s") + ", and %s second" + (seconds == 1 ? "" : "s") + ".", minutes, seconds);
        else if(seconds > 0)
            time = String.format("%s second" + (seconds == 1 ? "" : "s") + ".", seconds);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setAuthor("Uptime");
        builder.setDescription("Current uptime: " + time);

        handler.sendMessage(builder);
    }
}
