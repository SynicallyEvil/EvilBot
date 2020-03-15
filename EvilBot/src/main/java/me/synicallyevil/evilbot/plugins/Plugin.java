package me.synicallyevil.evilbot.plugins;

import me.synicallyevil.evilbot.EvilBotPlugin;

public class Plugin {

    private EvilBotPlugin plugin;
    private String name;
    private String version;
    private String author;


    /**
     * The constructor for this class.
     * @param plugin
     *        The EvilBotPlugin instance.
     */
    public Plugin(EvilBotPlugin plugin){
        this.plugin = plugin;
        this.name = plugin.pluginName();
        this.version = plugin.pluginVersion();
        this.author = plugin.pluginAuthor();
    }

    /**
     * Get's the plugin instance.
     * @return
     *        The EvilBotPlugin instance.
     */
    public EvilBotPlugin getPlugin(){
        return plugin;
    }

    /**
     * Get's the name of the plugin.
     * @return
     *        Plugin name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get's the version of the plugin.
     * @return
     *        Plugin version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get's the author of the plugin.
     * @return
     *        Plugin author.
     */
    public String getAuthor(){
        return author;
    }
}
