package me.synicallyevil.evilbot.listeners;

import me.synicallyevil.evilbot.EvilBot;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

import static me.synicallyevil.evilbot.utils.Utils.embedBuilder;

public class LoggingListeners extends ListenerAdapter {

    private EvilBot bot;

    public LoggingListeners(EvilBot bot){
        this.bot = bot;
    }

    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Message deleted: " + event.getJDA().getTextChannelCache().getElementById(event.getMessageId()));
    }

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Message updated: " + event.getMessage());
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Role created: " + event.getRole().getName());
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Role deleted: " + event.getRole().getName());
    }

    @Override
    public void onGuildBan(GuildBanEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Guild ban: " + event.getUser().getAsMention());
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event){
        // log.
        System.out.println(event.getGuild().getName() + ": Guild unban: " + event.getUser().getAsMention());
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        // log.
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event){
        // log.
    }
}
