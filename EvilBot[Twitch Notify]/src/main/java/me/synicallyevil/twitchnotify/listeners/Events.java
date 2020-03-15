package me.synicallyevil.twitchnotify.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Events extends ListenerAdapter {

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private List<String> usernames = new ArrayList<>();
    private String myid = "338881519198404609";

    private List<String> alreadyAnnounced = new ArrayList<>();

    @Override
    public void onReady(ReadyEvent event){
        usernames.add("sndwrx");
        usernames.add("nexus71c");
        usernames.add("synicallyevil");
        usernames.add("truenightmaregaming");

        Runnable runnable = () -> {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            User u = event.getJDA().getUserById(myid);

            for(String username : usernames){
                String s = connect("https://api.twitch.tv/helix/streams?user_login=" + username);

                JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                JsonElement el = jsonObject.get("data");

                if ((el.isJsonArray() && el.getAsJsonArray().size() > 0)){
                    if(!alreadyAnnounced.contains(username)){
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setDescription(username + " is streaming!\n\rLink: https://twitch.tv/" + username).setColor(Color.GREEN).setFooter("Notified at: " + formatter.format(date), null).setImage("https://static-cdn.jtvnw.net/previews-ttv/live_user_sndwrx-640x640.jpg");

                        u.openPrivateChannel().queue((channel) -> channel.sendMessage(builder.build()).queue());
                        System.out.println(s);
                        alreadyAnnounced.add(username);
                    }

                    //System.out.println(String.format("[%s] %s has been notified that %s is streaming.", formatter.format(date), u.getAsMention(), username));
                }else{
                    alreadyAnnounced.remove(username);
                    //System.out.println(String.format("[%s] %s: is NOT live.", formatter.format(date), username));
                }
            }
        };

        executor.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);
    }

    private String connect(String link){

        try{
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setDoOutput(true);
            con.setRequestProperty("Client-ID", "t0svyuj2aylp835615od94i66zvvn9p");

            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            StringBuilder builder = new StringBuilder();
            for (int line; (line = in.read()) >= 0;)
                builder.append((char)line);

            return builder.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
