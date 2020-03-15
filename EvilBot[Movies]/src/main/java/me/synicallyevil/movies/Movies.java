package me.synicallyevil.movies;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.movies.commands.MovieSuggest;
import me.synicallyevil.movies.data.StoredMovies;
import me.synicallyevil.movies.manager.MoviesManager;

public class Movies implements EvilBotPlugin {

    MoviesManager manager;
    StoredMovies storedMovies;

    @Override
    public String pluginName() {
        return "EvilBot Movies";
    }

    @Override
    public String pluginVersion() {
        return "1.0";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {
        this.manager = new MoviesManager(this);
        this.storedMovies = new StoredMovies();

        EvilBot.getBot().getApi().addCommand(new MovieSuggest(this));
    }

    public MoviesManager getManager() {
        return manager;
    }

    public StoredMovies getStoredMovies() {
        return storedMovies;
    }
}
