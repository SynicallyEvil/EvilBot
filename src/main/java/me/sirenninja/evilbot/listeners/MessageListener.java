package me.sirenninja.evilbot.listeners;

import me.sirenninja.evilbot.EvilBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static me.sirenninja.evilbot.utils.Utils.isInArray;

public class MessageListener extends ListenerAdapter {

    // The EvilBot instance.
    private EvilBot bot;

    // The default prefix. This will be moved when the guild
    // settings are created and finished.
    private char prefix = '!';

    // The constructor for this class.
    public MessageListener(EvilBot bot){
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMember().getUser().isBot() || event.getMember().getUser() == event.getJDA().getSelfUser())
            return;

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].charAt(0) == prefix){
            System.out.println(args[0].substring(1));
            bot.checkCommand(args[0].substring(1), event);
        }
    }
}