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

public class EightBall implements Command {

    private String[] aliases = {"eightball", "ball"};
    private boolean enabled = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean state) {
        enabled = state;
    }

    @Override
    public String getCommand() {
        return "8ball";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList(aliases);
    }

    @Override
    public void onCommand(String[] args, CommandHandler handler){
        StringBuilder sBuilder = new StringBuilder();

        for(int i = 1; i < args.length; i++)
            sBuilder.append(args[i]).append(" ");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(getRandomColor());
        builder.setFooter("Information generated at: " + handler.getEvent().getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), handler.getUser().getEffectiveAvatarUrl());
        builder.setDescription("**8Ball**")
                .addField("Question:", sBuilder.toString(), false)
                .addBlankField(false)
                .addField("8Ball Says:", getRandomAnswer(), false);

            handler.sendMessage(builder);
    }

    private String getRandomAnswer(){
        String[] replies = {
                "Yes.",
                "No.",
                "Maybe.",
                "Maybe not.",
                "It is certain.",
                "It is decidedly so.",
                "Without a doubt.",
                "Yes, definitely.",
                "Definitely not.",
                "You may rely on it.",
                "As I see it, Yes.",
                "Most likely.",
                "Outlook good.",
                "Signs point to yes.",
                "Don't count on it.",
                "My reply is no.",
                "My sources say no.",
                "Outlook not so good.",
                "Very doubtful."
        };

        Random random = new Random();
        return replies[random.nextInt(replies.length)];
    }
}
