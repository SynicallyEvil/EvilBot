package me.synicallyevil.evilbot.listeners;

import me.synicallyevil.evilbot.EvilBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ready extends ListenerAdapter {

    // The EvilBot instance.
    private EvilBot bot;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // The constructor for this class.
    public Ready(EvilBot bot){
        this.bot = bot;
    }

    @Override
    public void onReady(ReadyEvent event){
        event.getJDA().getGuilds().forEach(guilds -> bot.getApi().addGuild(guilds));

        // The runnable that checks the bots Game and sets it appropriately from the config.properties file.
        Runnable runnable = () -> {
            if(bot.getData().getGame() != null || bot.getData().getGame().equalsIgnoreCase("null"))
                event.getJDA().getPresence().setActivity(Activity.watching(bot.getData().getGame().replace("%servers%", String.valueOf(event.getJDA().getGuilds().size()))));
                //event.getJDA().getPresence().setPresence(OnlineStatus.valueOf(bot.getData().getStatus()), Game.watching(bot.getData().getGame().replace("%servers%", String.valueOf(event.getJDA().getGuilds().size()))), true);
        };

        executor.scheduleAtFixedRate(runnable, 0, 30, TimeUnit.SECONDS);
    }
}
