package me.synicallyevil.quotes.commands;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.quotes.Quotes;
import me.synicallyevil.quotes.manager.quote.Quote;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class QuoteCommand implements Command {
    private boolean enabled = true;

    public Quotes quote;

    public QuoteCommand(Quotes quote){
        this.quote = quote;
    }

    @Override
    public String getCommand() {
        return "quote";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("quotes");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public void onCommand(String[] strings, CommandHandler commandHandler) {
        if(!commandHandler.isGuild()){
            commandHandler.sendMessage(error("You're not in a guild."));
            return;
        }

        if(!(strings.length > 2)){
            help(commandHandler);
            return;
        }

        switch(strings[1].toLowerCase()){
            //case "-reload":
                //reload(commandHandler);
                //break;
            case "add":
                add(strings, commandHandler);
                break;
            case "remove":
                remove(strings, commandHandler);
                break;
            case "show":
                show(strings, commandHandler);
                break;
            //case "list":
                //list(commandHandler);
                //break;
            case "help":
            default:
                help(commandHandler);
        }
    }

    private void add(String[] strings, CommandHandler commandHandler){
        StringBuilder builder = new StringBuilder();
        for(int i = 2; i < strings.length; i++)
            builder.append(strings[i]).append(" ");

        builder.setLength(builder.length()-1);

        //QuoteManager manager = Quotes.getQuoteManager(commandHandler.getEvent().getGuild().getId());
        //manager.addQuote(builder.toString());

        Quote q = quote.getSQLite().saveQuote(commandHandler.getEvent().getGuild().getId(), builder.toString(), commandHandler.getUser().getId());
        if(q.getId() == -1){
            commandHandler.sendMessage(error("Failed to add quote.."));
            return;
        }

        commandHandler.sendMessage(success(String.format("Quote #%s has been added!", q.getId()), commandHandler, "Added by: %mention%! | Use \"!quote show " + q.getId() + "\"!"));
    }

    private void reload(CommandHandler commandHandler){
        //QuoteManager manager = Quotes.getQuoteManager(commandHandler.getEvent().getGuild().getId());

        if(!commandHandler.getMember().hasPermission(Permission.ADMINISTRATOR)){
            commandHandler.sendMessage(error("You don't have permission to reload the quotes for this server."));
            return;
        }

        //if(manager.reloadQuotes()){
            //commandHandler.sendMessage(success("Quotes for server ID (" + commandHandler.getEvent().getGuild().getId() + ") has been reloaded."));
            //return;
        //}

        commandHandler.sendMessage(error("Quotes for server ID (" + commandHandler.getEvent().getGuild().getId() + ") was unable to be reloaded. Must be an internal error."));
    }

    private void remove(String[] strings, CommandHandler commandHandler){
        if(!commandHandler.getMember().hasPermission(Permission.ADMINISTRATOR)){
            commandHandler.sendMessage(error("You don't have permission to remove quotes."));
            return;
        }

        if (!(strings[2].matches("\\d+"))) {
            commandHandler.sendMessage(error("That's not a number. Do !quote remove <number>"));
            return;
        }

        //QuoteManager manager = Quotes.getQuoteManager(commandHandler.getEvent().getGuild().getId());

        boolean fulldelete = false;
        if(strings[3] != null && strings[3].equalsIgnoreCase("-d"))
            fulldelete = true;

        if(quote.getSQLite().removeQuote(fulldelete, commandHandler.getEvent().getGuild().getId(), Integer.parseInt(strings[2])-1))
            commandHandler.sendMessage(success(String.format("Quote #%s has been removed!", Integer.parseInt(strings[2])-1), commandHandler, "Removed by: %mention%!"));
        else
            commandHandler.sendMessage(error("Failed to delete quote..."));

        if(commandHandler.getEvent().getChannelType().isGuild() && commandHandler.getEvent().getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE))
            commandHandler.getEvent().getMessage().delete().complete();
    }

    private void show(String[] strings, CommandHandler commandHandler) {
        if (!(strings[2].matches("\\d+"))) {
            commandHandler.sendMessage(error("That's not a number. Do !quote show <number>"));
            return;
        }

        //QuoteManager manager = Quotes.getQuoteManager(commandHandler.getEvent().getGuild().getId());
        Quote q = quote.getSQLite().getQuoteFromInt(commandHandler.getEvent().getGuild().getId(), (Math.max(Integer.parseInt(strings[2]), 1)));

        Timestamp time = new Timestamp(q.getTimestamp());
        Date date = new Date(time.getTime());
        //Date time = new java.util.Date(q.getTimestamp()*1000);

        String name = q.getSender();
        User user = commandHandler.getEvent().getJDA().getUserById(q.getSender());
        if(user != null)
            name = user.getName() + "#" + user.getDiscriminator();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("**Quote Information:**")
                .setColor(Color.BLUE).addField("Quote:", "*" + q.getQuote() + "*", true).setFooter("Submitted by: " + name + "\nSubmitted: " + date.toString(), (user != null ? user.getEffectiveAvatarUrl() : ""));

        commandHandler.sendMessage(builder);
    }

    private void list(CommandHandler commandHandler){

    }

    private void help(CommandHandler commandHandler){
        //commandHandler.sendMessage("*!quote add <message>*, *!quote remove <quote id>*, *!quote show <quote id>*. There, enjoy.");
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("**Quote Information:**")
                .setColor(Color.BLUE)
                .addField("Quote Commands:", "**!quote add <message>**\n**!quote remove <quote id>**\n**!quote show <quote id>**", true)
                .addField(" ", "- *Adds a quote.*\n- *Removes a quote.*\n- *Shows the quote.*", true);

        commandHandler.sendMessage(builder);
    }

    private EmbedBuilder error(String message){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("**Quote Error:**")
                .setColor(Color.RED).setDescription(message);

        return builder;
    }

    private EmbedBuilder success(String message){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("**Quote Success:**")
                .setColor(Color.GREEN).setDescription(message);

        return builder;
    }

    private EmbedBuilder success(String message, CommandHandler commandHandler, String footer){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("**Quote Success:**")
                .setColor(Color.GREEN).setDescription(message).setFooter(footer.replace("%mention%", commandHandler.getUser().getName() + "#" + commandHandler.getUser().getDiscriminator()), commandHandler.getUser().getAvatarUrl());

        return builder;
    }
}
