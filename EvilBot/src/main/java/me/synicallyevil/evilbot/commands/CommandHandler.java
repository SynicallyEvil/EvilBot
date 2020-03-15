package me.synicallyevil.evilbot.commands;

import me.synicallyevil.evilbot.EvilBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {

    private MessageReceivedEvent event;
    private EvilBot bot;

    public CommandHandler(EvilBot bot, MessageReceivedEvent event){
        this.bot = bot;
        this.event = event;
    }

    public EvilBot getEvilBot(){
        return bot;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public boolean isGuild(){
        return this.event.getChannelType().isGuild();
    }

    public User getUser(){
        return event.getAuthor();
    }

    public Member getMember(){
        return event.getMember();
    }

    public void sendMessage(String message){
        event.getChannel().sendMessage(message).complete();
    }

    public void sendMessage(EmbedBuilder builder){
        event.getChannel().sendMessage(builder.build()).complete();
    }

    public void sendMessage(EmbedBuilder builder, String... reactions){
        event.getChannel().sendMessage(builder.build()).queue(message -> {
            for(String s : reactions) {
                message.addReaction(s).queue();
            }
        });
    }
}
