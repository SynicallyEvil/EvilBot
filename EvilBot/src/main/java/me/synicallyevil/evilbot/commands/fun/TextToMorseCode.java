package me.synicallyevil.evilbot.commands.fun;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.evilbot.utils.morsecode.MorseCode;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Arrays;
import java.util.List;

import static me.synicallyevil.evilbot.utils.Utils.getRandomColor;

public class TextToMorseCode implements Command {

    private String[] aliases = {"ttm", "texttomorse"};
    private boolean enabled = true;

    @Override
    public String getCommand() {
        return "texttomorsecode";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList(aliases);
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
        StringBuilder message = new StringBuilder();

        for(int i = 1; i < args.length; i++)
            message.append(args[i]).append(" ");

        message.setLength(message.length() - 1);
        MorseCode mc = new MorseCode(true, message.toString());

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getRandomColor());
        builder.setDescription("**Text to Morse Code**")
                .addField("Text:", message.toString(), false)
                .addBlankField(false)
                .addField("Morse Code:", mc.convertToMorseCode(), true);

        handler.sendMessage(builder);
    }
}
