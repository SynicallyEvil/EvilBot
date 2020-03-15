package me.synicallyevil.movies.commands;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.movies.Movies;
import me.synicallyevil.movies.manager.Movie;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieSuggest implements Command {

    Movies movies;
    String messageid;

    public MovieSuggest(Movies movies){
        this.movies = movies;
    }

    @Override
    public String getCommand() {
        return "moviesuggest";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean state) {

    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        if(args.length < 2){
            return;
        }

        String year = "";
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            if(args[i].startsWith("year:")){
                year = args[i].split(":")[1].trim();
                continue;
            }

            builder.append(" ").append(args[i]);
        }

        String query = builder.toString().substring(1);
        Movie movie = movies.getManager().searchMovie(query, year);

        if(movie.isErrorPreset()){
            handler.sendMessage("Error: " + movie.getResponse());
            return;
        }

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int year_of_today = cal.get(Calendar.YEAR);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("" + movie.getTitle() + " - " + movie.getYear() + " | Rated: " + movie.getRated(), movie.getPoster(), movie.getPoster())
                .setColor(Color.CYAN).setImage(movie.getPoster())
                .addField("Released:", movie.getReleased() + (movie.getYear().equalsIgnoreCase(String.valueOf(year_of_today)) ? "\n(NEW)" : ""), true)
                .addField("Genre:", movie.getGenre(), true)
                .addField("Runtime:", movie.getRuntime(), true)
                //.addField("Rated:", movie.getRated(), true)
                .addBlankField(false)
                .addField("Language:", movie.getLanguage(), true)
                .addField("Country:", movie.getCountry(), true)
                .addField("Production:", movie.getProduction(), true)
                .addBlankField(false)
                .addField("IMDB Rating:", movie.getImdb_rating(), true)
                .addField("Rotten Tomatoes:", movie.getRotten_rating(), true)
                .addField("Metacritic Rating:", movie.getMetacritic_rating(), true)
                .addBlankField(false)
                .addField("Plot:", movie.getPlot(), false);

        handler.sendMessage(embedBuilder);
    }
}
