package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class About implements Command {

    @Override
    public String getCommand() {
        return "about";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean state) {

    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("About EvilBot:").addField("Owner:", "SynicallyEvil#0666\nID: 338881519198404609", true)
                .addField("Support:", "N/A", true)
                .addField("Donate:", "https://www.buymeacoff.ee/SynicallyEvil", true)
                .addBlankField(false)
                .addField("Description:", "N/A", false)
                .setColor(Color.MAGENTA);

        handler.sendMessage(builder);
    }
}
