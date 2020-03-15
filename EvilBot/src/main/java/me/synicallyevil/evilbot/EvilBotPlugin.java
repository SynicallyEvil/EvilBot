package me.synicallyevil.evilbot;

public interface EvilBotPlugin {

    // The plugin name that the user specifies.
    String pluginName();

    // The plugin version that the user specifies.
    String pluginVersion();

    // The plugin author.
    String pluginAuthor();

    // Whenever the plugin gets loaded, this is what runs.
    void onLoad();

}
