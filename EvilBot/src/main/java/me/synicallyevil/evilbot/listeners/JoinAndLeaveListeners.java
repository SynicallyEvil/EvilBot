package me.synicallyevil.evilbot.listeners;

import me.synicallyevil.evilbot.EvilBot;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinAndLeaveListeners extends ListenerAdapter {

    private EvilBot bot;

    public JoinAndLeaveListeners(EvilBot bot){
        this.bot = bot;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        /*GuildData guildData = bot.getApi().findGuild(event.getGuild());

        if(guildData.isJoinMessageEnabled())
            event.getGuild().getTextChannelsByName(guildData.getJoinAndLeaveMessageChannel(), true).get(0).sendMessage(embedBuilder("Member Has Joined!", " ", guildData.getJoinMessage(), null, getRandomColor()).build()).complete();
        */

        /*
        Member m = event.getGuild().getMember(event.getUser());

        OffsetDateTime a = m.getUser().getCreationTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime creationDate = LocalDateTime.ofInstant(a.toInstant(), a.getOffset());

        if(Math.abs(ChronoUnit.DAYS.between(creationDate, now)) < 7){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("New Account Detected!", event.getUser().getEffectiveAvatarUrl());
            builder.setDescription("New account detected (" + event.getUser().getName() + "#" + event.getUser().getDiscriminator() + "). Hours between creation date and now: " + Math.abs(ChronoUnit.HOURS.between(creationDate, now))  + ". User will be kicked.");
            builder.setColor(Color.RED);
            builder.setThumbnail(event.getUser().getEffectiveAvatarUrl());

            event.getGuild().getTextChannelsByName("logs", true).get(0).sendMessage(builder.build()).complete();

            event.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage("Your account is too new. We don't condone the use of \"fake\" or throwaway accounts. Please try again when your account is a little older.").queue());

            event.getGuild().getController().kick(m).reason("Your account is too new.").complete();
        }*/
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event){
        /*GuildData guildData = bot.getApi().findGuild(event.getGuild());

        if(guildData.isLeaveMessageEnabled())
            event.getGuild().getTextChannelsByName(guildData.getJoinAndLeaveMessageChannel(), true).get(0).sendMessage(embedBuilder("Member Has Left!", " ", guildData.getLeftMessage(), null, Color.RED).build()).complete();
    */

    }
}
