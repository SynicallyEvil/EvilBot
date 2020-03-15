package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class Donate implements Command {

    @Override
    public String getCommand() {
        return "donate";
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
        builder.setAuthor("Donate:").setDescription("You're definitely free (but not required) to donate!\nDonation link: https://www.buymeacoff.ee/SynicallyEvil").setColor(Color.GREEN);

        handler.sendMessage(builder);
    }
}
