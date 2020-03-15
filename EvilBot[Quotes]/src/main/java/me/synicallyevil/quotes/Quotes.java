package me.synicallyevil.quotes;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotAPI;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.quotes.commands.QuoteCommand;
import me.synicallyevil.quotes.data.IDatabase;
import me.synicallyevil.quotes.data.SQLite;
import me.synicallyevil.quotes.manager.QuoteManager;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;

public class Quotes implements EvilBotPlugin {

    //private static HashMap<String, QuoteManager> quoteManagers = new HashMap<>();
    private QuoteManager manager;
    private IDatabase database;

    @Override
    public String pluginName() {
        return "Quotes";
    }

    @Override
    public String pluginVersion() {
        return "2.0";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {
        EvilBotAPI api = EvilBot.getBot().getApi();
        this.manager = new QuoteManager();

        File folder = new File("plugins" + File.separator + "quotes");
        if(!(folder.exists()))
            folder.mkdir();

        api.addCommand(new QuoteCommand(this));

        new java.util.Timer().schedule(new java.util.TimerTask() {

            @Override
            public void run() {
                database = new SQLite(System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "quotes");

                for(Guild guild : api.getJDA().getGuilds())
                    database.createTable(guild.getId());
            }

        }, 2500);
    }

    public QuoteManager getQuoteManager(){
        return manager;
    }

    public SQLite getSQLite(){
        return (SQLite)database;
    }
}
