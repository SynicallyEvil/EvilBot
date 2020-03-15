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
import me.synicallyevil.evilbot.plugins.Plugin;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarFile;

public class EvilBot {

    /**
     * TODO:
     * Add settings for separate Guilds.
     * Add a system to disallow new accounts to join.
     * Add role support for the commands.
     * Add cooldown for commands. (possibly role based.)
     * Add more settings (changeable prefix, bot name, etc.)
     * Join / leave messages (and make them modifiable.)
     * Logs (kicks, bans, deletes, etc.)
     * Auto role.
     * Toggleable commands.
     * Verification like thing.
     * Custom responses for some commands.
     * ADD A DAMN HELP COMMAND.
     *
     *
     * TODO PROGRESSIVELY:
     * Add commands.
     *
     *
     * TODO EVENTUALLY:
     * Add music support.
     * Twitter / YouTube / Twitch / Mixer notifications.
     * Leveling system.
     */

    // The bot instance.
    private static EvilBot bot;

    // JDA, Data, and EvilBotAPI instances.
    private static JDA jda;
    private static Data data;
    private static EvilBotAPI api;

    // Plugin stuff.
    private static String pluginsFolder = "plugins";
    private static ArrayList<Plugin> plugins = new ArrayList<>();

    private static Long uptimeStarted;

    public static void main(String[] args) throws Exception{
        data = new Data();
        bot = new EvilBot();
        api = new EvilBotAPI(bot);
        uptimeStarted = System.currentTimeMillis();

        File folder = new File(pluginsFolder);
        if(!(folder.exists()))
            folder.mkdir();

        // Checks if the DISCORD API KEY was added.
        if(data.getKey().equalsIgnoreCase("DISCORD-API-KEY")) {
            System.out.println("Config has been created and loaded! Please look at the file and restart!");
            return;
        }

        // Builds the bot and starts it.
        jda = new JDABuilder(AccountType.BOT).setToken(data.getKey()).setStatus(OnlineStatus.valueOf(data.getStatus())).build();
        jda.getPresence().setActivity(Activity.watching("Initializing..."));
        jda.setAutoReconnect(true);

        addCommands();
        addListeners();

        loadPlugins(folder);
    }

    /**
     * Simply just a way to clean up the commands.
     * This is where all of the commands get added.
     */
    private static void addCommands(){
        getBot().getApi().addCommand(new EightBall(),
                new UserStats(),
                new CoinFlip(),
                new RockPaperScissors(),
                new Translator(),
                new MathEx(),
                new Ping(),
                new MorseCodeToText(),
                new TextToMorseCode(),
                //new Trivia(getBot()),
                new Uptime(),
                new Plugins(),
                new Donate(),
                new About());
    }

    /**
     * Simply just adds the premade listeners.
     */
    private static void addListeners(){
        getBot().getApi().addListener(new MessageListener(getBot()),
                new JoinAndLeaveListeners(getBot()),
                new Ready(getBot()),
                new LoggingListeners(getBot()));
    }

    /**
     * Loads the plugins.
     * @param folder
     *        Folder where the plugins reside.
     */
    private static void loadPlugins(File folder){
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));
        ArrayList<URL> urls = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();

        if(!(files == null)){
            Arrays.asList(files).forEach(file -> {
                try{
                    JarFile jarFile = new JarFile(file);
                    urls.add(new URL("jar:file:" + pluginsFolder + "/" + file.getName() + "!/"));

                    jarFile.stream().forEach(jarEntry -> classes.add(jarEntry.getName()));
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            });

            URLClassLoader pluginLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
            classes.forEach(s -> {
                try{
                    Class clazz = pluginLoader.loadClass(s.replaceAll("/", ".").replace(".class", ""));
                    Class[] interfaces = clazz.getInterfaces();

                    for(Class face : interfaces){
                        if(face == EvilBotPlugin.class){
                            EvilBotPlugin plugin = (EvilBotPlugin) clazz.newInstance();
                            plugin.onLoad();

                            plugins.add(new Plugin(plugin));

                            System.out.println(String.format("Plugin loaded: %s [%s] by %s", plugin.pluginName(), plugin.pluginVersion(), plugin.pluginAuthor()));
                        }
                    }
                }catch(Exception ex){
                    if(ex instanceof ClassNotFoundException)
                        return;

                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * Checks the command when someone uses the bot's prefix.
     * @param command
     *        The first argument of the message.
     * @param event
     *        The MessageReceivedEvent event.
     */
    public void checkCommand(String command, String message, MessageReceivedEvent event){
        List<String> argsList = new LinkedList<>(Arrays.asList(message.split(" ")));

        if(argsList.get(0).equalsIgnoreCase(getJDA().getSelfUser().getAsMention()))
            argsList.remove(0);

        for(Command c : api.getCommands()){
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

    /**
     * The instance of the Data.
     * @return
     *        The Data instance.
     */
    public Data getData(){
        return data;
    }

    /**
     * The instance of the current JDA.
     * @return
     *        The JDA instance.
     */
    public JDA getJDA(){
        return jda;
    }

    /**
     * The  instance of the Bot.
     * @return
     *        The Bot instance.
     */
    public static EvilBot getBot() {
        return bot;
    }

    /**
     * The instance of the API.
     * @return
     *        The API instance.
     */
    public EvilBotAPI getApi(){
        return api;
    }

    public List<Plugin> getPlugins(){
        return plugins;
    }

    public Long getUptimeStarted(){
        return uptimeStarted;
    }
}
