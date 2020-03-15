package me.synicallyevil.quotes.data;

import me.synicallyevil.quotes.manager.quote.Quote;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabase {
    Connection getConnection() throws SQLException;
    void createTable(String server_id);
    Quote getQuoteFromInt(String server_id, int id);
    Quote saveQuote(String server_id, String quote, String submitter);
    boolean removeQuote(boolean fulldelete, String server_id, int id);
}