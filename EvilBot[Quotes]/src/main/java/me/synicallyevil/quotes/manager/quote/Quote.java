package me.synicallyevil.quotes.manager.quote;

public class Quote {

    private int id;
    private String quote;
    private String sender;
    private long timestamp;

    public Quote(int id, String quote, String sender, long timestamp){
        this.id = id;
        this.quote = quote;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
