package me.synicallyevil.movies.manager;

public class Movie {

    private String title, year, plot, rated, released, runtime, genre, language, country, imdb_rating, rotten_rating, metacritic_rating, production, poster, response;
    private boolean error = false;

    public Movie(String title, String year, String plot, String rated, String released, String runtime, String genre, String language, String country, String imdb_rating, String rotten_rating, String metacritic_rating, String production, String poster){
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.language = language;
        this.country = country;
        this.imdb_rating = imdb_rating;
        this.rotten_rating = rotten_rating;
        this.metacritic_rating = metacritic_rating;
        this.production = production;
        this.poster = poster;
    }

    public Movie(boolean error, String response){
        this.error = error;
        this.response = response;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPlot() {
        return plot;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getImdb_rating() {
        return imdb_rating;
    }

    public String getRotten_rating() {
        return rotten_rating;
    }

    public String getMetacritic_rating() {
        return metacritic_rating;
    }

    public String getProduction() {
        return production;
    }

    public boolean isErrorPreset() {
        return error;
    }

    public String getResponse() {
        return response;
    }

    public String getPoster() {
        return poster;
    }
}
