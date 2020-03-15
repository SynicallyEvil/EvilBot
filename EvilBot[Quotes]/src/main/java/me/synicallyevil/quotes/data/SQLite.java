package me.synicallyevil.quotes.data;

import me.synicallyevil.quotes.manager.quote.Quote;

import java.io.File;
import java.sql.*;

public class SQLite implements IDatabase{

    // serverid-id, quote, time, sender
    private Connection connection;
    private String path;

    public SQLite(String path){
        this.path = path;
        try{
            connection = getConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(this.connection != null && !this.connection.isClosed()){
            return this.connection;
        }

        File file = new File(path, "QuotesDB.db");
        if(!new File(path).exists()){
            new File(path).mkdirs();
        }

        if(!(file.exists())){
            try{
                file.createNewFile();
            }catch (Exception ignored){}
        }

        try{
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + new File(path, "QuotesDB.db").getPath());
            //System.out.println("Connection closed? " + connection.isClosed());
            //System.out.println("Path of database: " + new File(path, "QuotesDB.db").getPath());
            return this.connection;
        }catch (ClassNotFoundException ex){
            System.out.println("Couldn't find the SQLite library!");
        }

        return null;
    }

    private void disableConnection(){
        try {
            if((connection != null) && !(connection.isClosed()))
                connection.close();

            System.out.println("Connection disabled!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String server_id){
        //System.out.println("Attempting to create database for " + server_id + "...");
        //String createTable = "CREATE TABLE IF NOT EXISTS `" + server_id + "` (id INTEGER PRIMARY KEY AUTO_INCREMENT, quote TEXT(65535), submitted-by varchar(255), submitted-time SIGNED_BIGINT(20));";
        String createTable = "CREATE TABLE IF NOT EXISTS `x" + server_id + "` (`id` INTEGER not NULL, `quote` TEXT(10000), `submitted-by` VARCHAR(255), `submitted-time` SIGNED_BIGINT(20), PRIMARY KEY (id));";

        try {
            PreparedStatement table = connection.prepareStatement(createTable);
            table.executeUpdate();
            table.close();

            //System.out.println("Database created for server id: " + server_id + "!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Quote getQuoteFromInt(String server_id, int id){
        //createTable(server_id);

        String sql = "SELECT * FROM `x" + server_id + "` WHERE `id`='" + id + "';";

        try{
            PreparedStatement prepare = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = prepare.executeQuery();

            if(rs.next()){
                int q_id = rs.getInt("id");
                String quote = rs.getString("quote");
                String sender = rs.getString("submitted-by");
                long timestamp = rs.getLong("submitted-time");

                System.out.println("id: " + q_id + " | sender: " + sender + " | timestamp: " + timestamp + " | quote: " + quote);

                prepare.close();
                rs.close();
                return new Quote(q_id, quote, sender, timestamp);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Quote(-1, null, null, -1);
    }

    @Override
    public Quote saveQuote(String server_id, String quote, String submitter) {
        //createTable(server_id);

        String sql = "INSERT INTO `x" + server_id + "` (`quote`, `submitted-by`, `submitted-time`) VALUES(?,?,?);";

        try{
            PreparedStatement prepare = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepare.setString(1, quote);
            prepare.setString(2, submitter);
            prepare.setLong(3, System.currentTimeMillis());

            prepare.executeUpdate();

            PreparedStatement prepare2 = connection.prepareStatement("SELECT `id` FROM `x" + server_id + "`;", Statement.RETURN_GENERATED_KEYS);
            ResultSet results = prepare2.executeQuery();

            int i = 0;
            while(results.next())
                i++;

            prepare.close();
            prepare2.close();
            results.close();

            return new Quote(i, quote, submitter, System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Quote(-1, null, null, -1);
    }

    @Override
    public boolean removeQuote(boolean fulldelete, String server_id, int id) {
        //createTable(server_id);

        String sql = "SELECT * FROM `x" + server_id + "` WHERE `id`=?;";

        try{
            PreparedStatement prepare = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepare.setInt(1, id);

            ResultSet results = prepare.executeQuery();

            if(results.next()){
                if(fulldelete){
                    prepare.executeUpdate("DELETE FROM x" + server_id + " WHERE id='" + id + "';");
                }else{
                    prepare.executeUpdate("UPDATE `x" + server_id + "` SET `quote`='*Quote removed by an Administrator.*' WHERE `id`='" + id + "';");
                }

                prepare.close();
                results.close();
                return true;
            }

            prepare.close();
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
