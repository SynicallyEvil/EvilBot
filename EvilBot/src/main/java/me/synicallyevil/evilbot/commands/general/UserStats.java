package me.synicallyevil.evilbot.commands.general;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UserStats implements Command {

    private String[] aliases = {"userstats", "stat", "stats"};
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
        return "userstat";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList(aliases);
    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        if(!(handler.isGuild())){
            System.out.println(handler.getEvent().getChannelType());
            return;
        }

        Member member = handler.getMember();
        String game = "null";

        try{
            //game = member.getGame().getName();
            game = "(not currently working)";
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(member.getColor());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        builder.setFooter("Information generated at: " + handler.getEvent().getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), member.getUser().getEffectiveAvatarUrl());
        builder.setDescription("**User information (__" + member.getUser().getName() + "__):**")
                .addField("Name:", member.getUser().getName() + "#" + member.getUser().getDiscriminator(), true)
                .addField("Nickname:", member.getEffectiveName(), true)
                .addField("ID:", member.getUser().getId(), true)
                .addBlankField(false)
                .addField("Current Status:", member.getOnlineStatus().getKey(), true)
                .addField("Current Game:", game, true)
                .addBlankField(false)
                .addField("Joined Discord:", member.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true)
                .addField("Joined This Guild:", member.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);

        handler.sendMessage(builder);
    }
}
