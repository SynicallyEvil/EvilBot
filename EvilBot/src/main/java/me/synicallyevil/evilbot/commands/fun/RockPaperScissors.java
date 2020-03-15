package me.synicallyevil.evilbot.commands.fun;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static me.synicallyevil.evilbot.utils.Utils.arrayContains;
import static me.synicallyevil.evilbot.utils.Utils.capitalizeFirstCharacter;

public class RockPaperScissors implements Command {

    private boolean enabled = true;
    private String[] aliases = {"rps"};

    @Override
    public String getCommand() {
        return "rockpaperscissors";
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
        String[] correctArgs = {"rock", "paper", "scissors"};

        if(!(arrayContains(correctArgs, args[1].toLowerCase()))){
            handler.sendMessage(handler.getUser().getAsMention() + ", please use !rps [rock, paper, scissors].");
            return;
        }

        Random random = new Random();
        int decision = random.nextInt(correctArgs.length);

        String user = args[1];
        String bot = correctArgs[decision];

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor((user.equalsIgnoreCase(bot) ? Color.GRAY : (didUserWin(user, bot) ? Color.GREEN : Color.RED)));
        builder.setThumbnail(handler.getUser().getEffectiveAvatarUrl());
        builder.setFooter("Information generated at: " + handler.getEvent().getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), handler.getUser().getEffectiveAvatarUrl());
        builder.setDescription("**Rock Paper Scissors**")
                .addField("You chose:", capitalizeFirstCharacter(user), true)
                .addField("Bot chose:", capitalizeFirstCharacter(bot), true)
                .addBlankField(false)
                .addField("Result:", (user.equalsIgnoreCase(bot) ? "Draw!" : (didUserWin(user, bot) ? "You won!" : "Bot won!")), false);

        handler.sendMessage(builder);
    }

    private boolean didUserWin(String user, String bot){
        if(user.equalsIgnoreCase("rock") && bot.equalsIgnoreCase("scissors"))
            return true;
        else if(user.equalsIgnoreCase("paper") && bot.equalsIgnoreCase("rock"))
            return true;
        else if(user.equalsIgnoreCase("scissors") && bot.equalsIgnoreCase("paper"))
            return true;

        return false;
    }
}
