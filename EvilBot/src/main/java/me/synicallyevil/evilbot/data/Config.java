package me.synicallyevil.evilbot.data;

public class Config {

    // Discord settings
    private String botkey;
    private String status;
    private String game;

    // Twitter settings
    private String consumerkey;
    private String consumersecret;
    private String accesstoken;
    private String accesssecret;

    public String getBotkey() {
        return botkey;
    }

    public void setBotkey(String botkey) {
        this.botkey = botkey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getConsumerkey() {
        return consumerkey;
    }

    public void setConsumerkey(String consumerkey) {
        this.consumerkey = consumerkey;
    }

    public String getConsumersecret() {
        return consumersecret;
    }

    public void setConsumersecret(String consumersecret) {
        this.consumersecret = consumersecret;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getAccesssecret() {
        return accesssecret;
    }

    public void setAccesssecret(String accesssecret) {
        this.accesssecret = accesssecret;
    }
}
