package me.synicallyevil.evilbot.commands.fun;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static me.synicallyevil.evilbot.utils.Utils.getRandomColor;

public class CoinFlip implements Command {

    private boolean enabled = true;
    private String[] aliases = {"cf"};

    @Override
    public String getCommand() {
        return "coinflip";
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
        // Lulz... the more there are, the more random it'll be, I guess.
        String[] sides = {"Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", "Heads", "Tails", };

        Random random = new Random();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getRandomColor());
        builder.setThumbnail(handler.getUser().getEffectiveAvatarUrl());
        builder.setFooter("Information generated at: " + handler.getEvent().getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), handler.getUser().getEffectiveAvatarUrl());
        builder.setDescription("**Coin Flip**")
                .addField("Side Chosen:", sides[random.nextInt(sides.length)], false);

        handler.sendMessage(builder);
    }
}
