package me.synicallyevil.evilbot.data;

import com.google.gson.Gson;

import java.io.*;

import static me.synicallyevil.evilbot.utils.Utils.*;

public class Data {

    // Filename.
    private String filename;

    Config config;

    public Data(){
        config = new Config();
        filename = System.getProperty("user.dir") + File.separator + "config.json";
        loadConfig();
    }

    private void loadConfig(){
        try{
            Gson gson = new Gson();
            config.setBotkey("N/A");
            config.setGame("%servers% online servers!");
            config.setStatus("ONLINE");

            /*

            if(new File(filename).exists()){
                BufferedReader br = new BufferedReader(new FileReader(filename));
                config = gson.fromJson(br, Config.class);
            }else{
                //config.setBotkey("DISCORD-API-KEY");
                //config.setGame("null");
                //config.setStatus("ONLINE");

                config.setBotkey("NDEwODg2OTk0NjQ2OTI1MzEz.DVzr4Q.7revTl7aacyKXh4LERAk9C90rhk");
                config.setGame("%servers% online servers!");
                config.setStatus("ONLINE");

                String json = gson.toJson(config);

                FileWriter writer = new FileWriter(filename);
                writer.write(json);
                writer.close();
            }*/
        }catch(Exception e){
            System.out.println("loadConfig() exception: " + e.getMessage());
        }
    }

    public String getKey(){
        return config.getBotkey();
    }

    public String getStatus(){
        String[] statuses = {"ONLINE", "OFFLINE", "DO_NOT_DISTURB", "IDLE", "INVISIBLE", "UNKNOWN"};

        if(arrayContains(statuses, config.getStatus()))
            return config.getStatus().toUpperCase();

        return "ONLINE";
    }

    public String getGame(){
        return config.getGame();
    }

    public String getTwitterConsumerkey(){
        return config.getConsumerkey();
    }

    public String getTwitterConsumerSecret(){
        return config.getConsumersecret();
    }

    public String getTwitterAccessToken(){
        return config.getAccesstoken();
    }

    public String getTwitterAccessSecret(){
        return config.getAccesssecret();
    }

}
