package me.synicallyevil.evilbot;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.evilbot.commands.fun.*;
import me.synicallyevil.evilbot.commands.general.*;
import me.synicallyevil.evilbot.data.Data;
import me.synicallyevil.evilbot.listeners.JoinAndLeaveListeners;
import me.synicallyevil.evilbot.listeners.LoggingListeners;
import me.synicallyevil.evilbot.listeners.MessageListener;
import me.synicallyevil.evilbot.listeners.Ready;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManager;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EvilBotJDA {

    private EvilBot bot;
    private JDA jda;

    protected static EvilBotJDA instance;

    public EvilBotJDA(EvilBot bot, Data data){
        instance = this;

        this.bot = bot;
        boolean sharding = true;
        int maxSharding = 2;

        // Checks if the DISCORD API KEY was added.
        if(data.getKey().equalsIgnoreCase("DISCORD-API-KEY")) {
            System.out.println("Config has been created and loaded! Please look at the file and restart!");
            return;
        }

        try{
            //if(sharding){
                // Builds, shards, and starts the bot.
                /*shardManagerBuilder = new DefaultShardManagerBuilder();
                shardManagerBuilder.setToken(data.getKey());
                shardManagerBuilder.setStatus(OnlineStatus.valueOf(data.getStatus()));
                shardManagerBuilder.setAutoReconnect(true);
                shardManagerBuilder.build();*/
            //}else{
                // Builds the bot and starts it.
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(data.getKey()).setStatus(OnlineStatus.valueOf(data.getStatus()));
            builder.setAutoReconnect(true);

            System.out.println("Initializing max shard count: " + maxSharding);
            if(sharding) {
                for (int i = 0; i < maxSharding; i++) {
                    jda = builder.useSharding(i, maxSharding).build();
                }

            }else{
                jda = builder.build();
                jda.getPresence().setActivity(Activity.watching("Initializing..."));
            }
            System.out.println("INFO: Awaiting...");
            jda.awaitReady();
            System.out.println("INFO: Awaited! Loading commands and listeners!");
            System.out.println(" ");

            addCommands();
            addListeners();
            System.out.println("INFO: Commands and listeners loaded!");
            System.out.println(" ");

            System.out.println("INFO: Loading plugins!");
            bot.loadPlugins();
            //}
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the command when someone uses the bot's prefix.
     * @param command
     *        The first argument of the message.
     * @param message
     *       The entire message, including the command.
     * @param event
     *        The MessageReceivedEvent event.
     */
    public void checkCommand(String command, String message, MessageReceivedEvent event){
        List<String> argsList = new LinkedList<>(Arrays.asList(message.split(" ")));

        if(argsList.get(0).equalsIgnoreCase(event.getJDA().getSelfUser().getAsMention()))
            argsList.remove(0);

        for(Command c : bot.getApi().getCommands()){
            List<String> aliases = c.getAliases();

            try{
                if(c.getCommand().equalsIgnoreCase(command) || (!aliases.isEmpty() && aliases.contains(command.toLowerCase()))){
                    if(!(c.isEnabled()))
                        return;

                    //if(event.getChannelType().isGuild() && event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE))
                    //event.getMessage().delete().complete();

                    c.onCommand(argsList.toArray(new String[0]), new CommandHandler(bot, event));
                    return;
                }
            }catch(Exception ignored){}
        }
    }

    public void addListener(ListenerAdapter clazz){
        try {
            jda.awaitReady();

            jda.addEventListener(clazz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addListener(ListenerAdapter... classes){
        try {
            jda.awaitReady();

            for(ListenerAdapter clazz : classes)
                jda.addEventListener(clazz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simply just a way to clean up the commands.
     * This is where all of the commands get added.
     */
    private void addCommands(){
        bot.getApi().addCommand(new EightBall(),
                new UserStats(),
                new CoinFlip(),
                new RockPaperScissors(),
                new Translator(),
                new MathEx(),
                new Ping(),
                new MorseCodeToText(),
                new TextToMorseCode(),
                new Uptime(),
                new Plugins(),
                new Donate(),
                new About());
    }

    /**
     * Simply just adds the premade listeners.
     */
    private void addListeners(){
        addListener(new MessageListener(bot),
                new JoinAndLeaveListeners(bot),
                new Ready(bot),
                new LoggingListeners(bot));
    }

    public JDA getJDA() {
        return jda;
    }
}
