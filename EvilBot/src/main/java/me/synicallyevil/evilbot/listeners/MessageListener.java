package me.synicallyevil.evilbot.listeners;

import me.synicallyevil.evilbot.EvilBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    // The EvilBot instance.
    private EvilBot bot;

    // The default prefix. This will be moved when the guild
    // settings are created and finished.

    // The constructor for this class.
    public MessageListener(EvilBot bot){
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot() || event.getAuthor() == event.getJDA().getSelfUser() || event.getMessage().getAttachments().size() > 0)
            return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        char c = (event.getChannelType().isGuild() ? bot.getApi().findGuild(event.getGuild()).getPrefix() : '!');

        if(args[0].charAt(0) == c){
            bot.getJdaLoader().checkCommand(args[0].substring(1), event.getMessage().getContentRaw(), event);
        }else if(args[0].equalsIgnoreCase(event.getJDA().getSelfUser().getAsMention())){
            bot.getJdaLoader().checkCommand(args[1], event.getMessage().getContentRaw(), event);
        }
    }
}
