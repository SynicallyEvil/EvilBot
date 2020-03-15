package me.synicallyevil.movies.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.synicallyevil.movies.manager.Movie;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class StoredMovies {

    private String filename;

    public StoredMovies(){
        filename = System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "cached_movies" + File.separator;

        File file = new File(filename);
        try{
            file.mkdirs();
        }catch (Exception ignored){}
    }

    public boolean doesMovieExistInLocalStorage(String title){
        File file = new File(filename, title.replace(":", "") + ".json");
        return file.exists();
    }

    public boolean doesMovieExistInLocalStorage(String title, String year){
        File file = new File(filename, title.replace(":", "") + "-" + year + ".json");
        return file.exists();
    }

    public void saveMovie(String title, String content){
        File movie_file = new File(filename, title.replace(":", "") + ".json");

        try (FileWriter file = new FileWriter(movie_file)) {
            file.write(content);
            file.flush();

            System.out.println("Movie was saved. (" + title + ")");
        } catch (Exception ex) {
            System.out.println("Movie was unable to be saved. (" + title + "): " + ex.getMessage());
        }
    }

    public void saveMovie(String title, String year, String content){
        File movie_file = new File(filename, title.replace(":", "") + "-" + year + ".json");

        try (FileWriter file = new FileWriter(movie_file)) {
            file.write(content);
            file.flush();

            System.out.println("Movie was saved. (" + title + "-" + year + ")");
        } catch (Exception ex) {
            System.out.println("Movie was unable to be saved. (" + title + "-" + year + "): " + ex.getMessage());
        }
    }

    public Movie getLocalMovie(String title){
        System.out.println("Loading stored movie. (" + title + ")");

        File file = new File(filename, title.replace(":", "") + ".json");
        return grabMovieJson(file);
    }

    public Movie getLocalMovie(String title, String year){
        System.out.println("Loading stored movie. (" + title + "-" + year + ")");

        File file = new File(filename, title.replace(":", "") + "-" + year + ".json");
        return grabMovieJson(file);
    }

    private Movie grabMovieJson(File file){
        try (FileReader reader = new FileReader(file)){
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

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

            return new Movie(title, title_year, plot, rated, released, runtime, genre, language, country, imdb_rating, rotten_rating, metacritic_rating, production, poster);
        }catch (Exception ignored){}

        return new Movie(true, "Something went wrong while grabbing the cached movie.");
    }

}
