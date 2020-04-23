package me.synicallyevil.evilbot;

import me.synicallyevil.evilbot.commands.fun.*;
import me.synicallyevil.evilbot.commands.general.*;
import me.synicallyevil.evilbot.data.Data;
import me.synicallyevil.evilbot.listeners.JoinAndLeaveListeners;
import me.synicallyevil.evilbot.listeners.LoggingListeners;
import me.synicallyevil.evilbot.listeners.MessageListener;
import me.synicallyevil.evilbot.listeners.Ready;
import me.synicallyevil.evilbot.plugins.Plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static Data data;
    private static EvilBotAPI api;
    private static EvilBotJDA jdaLoader;

    private static String libraryFolder = "libraries";

    // Plugin stuff.
    public String pluginsFolder = "plugins";
    private static ArrayList<Plugin> plugins = new ArrayList<>();

    private static Long uptimeStarted;

    public static void main(String[] args) throws Exception{
        System.out.println("+----------------------------------+");
        System.out.println("INFO: Loading EvilBot - Version: 1.0");
        System.out.println("+----------------------------------+");


        //System.out.println("Loading?");
        //File lib = new File(libraryFolder);
        //if(!(lib.exists()))
            //lib.mkdir();

        //loadLibraries(lib);

        data = new Data();
        bot = new EvilBot();
        api = new EvilBotAPI(bot);
        uptimeStarted = System.currentTimeMillis();
        System.out.println("INFO: Data loaded!");
        System.out.println("+----------------------------------+");

        System.out.println("INFO: Loading JDA Loader!");
        jdaLoader = new EvilBotJDA(bot, data);
        System.out.println("INFO: JDA Loader initialized!");
        System.out.println("+----------------------------------+");
    }

    public void loadLibraries(File folder){
        System.out.println("Trying to load, or?");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));
        ArrayList<URL> urls = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();

        if(files == null)
            return;

        Arrays.asList(files).forEach(file -> {
            try{
                System.out.println("Attempting to add " + file.getName() + "..");
                JarFile jarFile = new JarFile(file);
                urls.add(new URL("jar:file:" + libraryFolder + "/" + file.getName() + "!/"));

                jarFile.stream().forEach(jarEntry -> classes.add(jarEntry.getName()));
                System.out.println("Added " + file.getName() + "?");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        URLClassLoader libraryLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
        classes.forEach(s -> {
            try{
                libraryLoader.loadClass(s.replaceAll("/", ".").replace(".class", ""));
            }catch(Exception ex){
                if(ex instanceof ClassNotFoundException)
                    return;

                ex.printStackTrace();
            }
        });
    }

    /**
     * Loads the plugins.
     */
    public void loadPlugins(){
        File folder = new File(pluginsFolder);
        if(!(folder.exists()))
            folder.mkdir();

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
     * The instance of the Data.
     * @return
     *        The Data instance.
     */
    public Data getData(){
        return data;
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

    public EvilBotJDA getJdaLoader() {
        return jdaLoader;
    }

    public List<Plugin> getPlugins(){
        return plugins;
    }

    public Long getUptimeStarted(){
        return uptimeStarted;
    }
}
