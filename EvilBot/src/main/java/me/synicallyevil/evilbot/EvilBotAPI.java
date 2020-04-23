package me.synicallyevil.evilbot;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.data.GuildData;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvilBotAPI {

    // The EvilBotAPI instance.
    private EvilBotAPI api;

    // The EvilBot instance.
    private EvilBot bot;

    // The List of commands (used by classes.)
    private List<Command> commands = new ArrayList<>();

    // The HashMap that contains all the Guilds, and all if their data.
    private HashMap<Guild, GuildData> guildData = new HashMap<>();

    /**
     * Allows other programs to hook into EvilBot and use the API to add commands and listeners.
     * @param bot
     *        The EvilBot instance.
     */
    EvilBotAPI(EvilBot bot){
        if(api != null)
            api = this;

        this.bot = bot;
    }

    /**
     * Grabs the API so other programs and hook into EvilBot.
     * @return
     *        The API instance.
     */
    public EvilBotAPI getAPI(){
        return api;
    }

    /**
     * Grabs the JDA instance from the Evilbot class.
     * @return
     *        The JDA instance.
     */
    public JDA getJDA(){
        return EvilBotJDA.instance.getJDA();
    }

    /**
     * Adds a command (or technically "class".)
     * @param clazz
     *        The class that implements Command.
     */
    public void addCommand(Command clazz){
        if(!(commands.contains(clazz)))
            commands.add(clazz);
    }

    /**
     * Adds multiple commands at the same time.
     * @param classes
     *        A list of classes that implement Command.
     */
    public void addCommand(Command... classes){
        for(Command clazz : classes)
            addCommand(clazz);
    }

    /**
     * Adds a listener.
     * @param clazz
     *        A class that implements ListenerAdapter.
     */
    public void addListener(ListenerAdapter clazz){
        EvilBotJDA.instance.addListener(clazz);
    }

    /**
     * Adds multiple listeners at the same time.
     * @param classes
     *        A list of classes that implement ListenerAdapter.
     */
    public void addListener(ListenerAdapter... classes){
        for(ListenerAdapter clazz : classes)
            EvilBotJDA.instance.addListener(clazz);
    }

    /**
     * Sets the enabled state of the command.
     * @param clazz
     *        The class that implements Command.
     * @param state
     *        The boolean state to change the current state of the command.
     */
    public void setEnabled(Command clazz, Boolean state){
        clazz.setEnabled(state);
    }

    /**
     * Gets the boolean state of the command.
     * @param clazz
     *        The class that implements Command.
     * @return
     *        The enabled state of the command.
     */
    public boolean isEnabled(Command clazz){
        return clazz.isEnabled();
    }

    /**
     * Adds a guild to the HashMap.
     * @param guild
     *        The Discords Guild.
     */
    public void addGuild(Guild guild){
        guildData.put(guild, new GuildData(guild));
    }

    /**
     * Adds a collection of guilds to the HashMap.
     * @param guilds
     *        The collection of Discord Guilds.
     */
    public void addGuild(Guild... guilds){
        for(Guild guild : guilds)
            addGuild(guild);
    }

    /**
     * Finds the Guild's data (If the data doesn't exist, it'll create it and return the new one.)
     * @param guild
     *        The Discord servers Guild.
     * @return
     *        The Guild's data.
     */
    public GuildData findGuild(Guild guild){
        if(!guildData.containsKey(guild))
            addGuild(guild);

        return guildData.get(guild);
    }

    /**
     * Get's the commands List.
     * @return
     *        The commands List.
     */
    public List<Command> getCommands(){
        return commands;
    }

    /**
     * Grabs the entire guildData HashMap.
     * @return
     *        The guildData HashMap.
     */
    public HashMap<Guild, GuildData> getGuildData(){
        return guildData;
    }
}
