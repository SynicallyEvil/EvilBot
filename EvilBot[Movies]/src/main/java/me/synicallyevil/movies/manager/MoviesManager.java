package me.synicallyevil.movies.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.synicallyevil.movies.Movies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MoviesManager {

    private String link = "http://www.omdbapi.com/?apikey={api_key}&t={query}";
    private String apikey = "bb1fa466";

    private Movies movies;

    public MoviesManager(Movies movies){
        this.movies = movies;
    }

    public Movie searchMovie(String query, String year){
        if(year != null && !year.isEmpty()) {
            link += "&y=" + year;

            if(movies.getStoredMovies().doesMovieExistInLocalStorage(query, year)){
                return movies.getStoredMovies().getLocalMovie(query, year);
            }
        }else{
            if(movies.getStoredMovies().doesMovieExistInLocalStorage(query)){
                return movies.getStoredMovies().getLocalMovie(query);
            }
        }

        String result = connect(link.replace("{api_key}", apikey).replace("{query}", query));
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();

        if(!(jsonObject.get("Response").getAsBoolean())){
            return new Movie(true, jsonObject.get("Error").getAsString());
        }

        //System.out.println("Title: " + jsonObject.get("Title").getAsString());

        String title = jsonObject.get("Title").getAsString(); //
        String title_year = jsonObject.get("Year").getAsString(); //
        String plot = jsonObject.get("Plot").getAsString();
        String rated = jsonObject.get("Rated").getAsString(); //
        String released = jsonObject.get("Released").getAsString(); //
        String runtime = jsonObject.get("Runtime").getAsString(); //
        String genre = jsonObject.get("Genre").getAsString(); //
        String language = jsonObject.get("Language").getAsString(); //
        String country = jsonObject.get("Country").getAsString(); //
        String imdb_rating = ""; //
        String rotten_rating = ""; //
        String metacritic_rating = ""; //
        String production = (jsonObject.get("Production").getAsString() == null ? "N/A" : jsonObject.get("Production").getAsString()); //
        String poster = jsonObject.get("Poster").getAsString(); //

        JsonArray ratings = jsonObject.getAsJsonArray("Ratings");
        if(ratings.size() > 0){
            for(int i = 0; i < ratings.size(); i++){
                JsonObject rate = ratings.get(i).getAsJsonObject();

                if(rate.get("Source").getAsString().equalsIgnoreCase("Internet Movie Database"))
                    imdb_rating = rate.get("Value").getAsString();

                if(rate.get("Source").getAsString().equalsIgnoreCase("Rotten Tomatoes"))
                    rotten_rating = rate.get("Value").getAsString();

                if(rate.get("Source").getAsString().equalsIgnoreCase("Metacritic"))
                    metacritic_rating = rate.get("Value").getAsString();
            }
        }

        if(year != null && !year.isEmpty())
            movies.getStoredMovies().saveMovie(title, title_year, result);
        else
            movies.getStoredMovies().saveMovie(title, result);

        return new Movie(title, title_year, plot, rated, released, runtime, genre, language, country, imdb_rating, rotten_rating, metacritic_rating, production, poster);
    }

    private String connect(String link){
        try{
            URL url = new URL(link.replace(" ", "%20"));
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            //con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
            //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();
            for (int line; (line = in.read()) >= 0;)
                builder.append((char)line);

            //System.out.println(builder.toString());
            in.close();
            con.disconnect();
            return builder.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
