package me.synicallyevil.trivia.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.util.Date;

public class Utils {

    public static EmbedBuilder triviaBuilder(Color color, Date date, Member member, String question, String category, String difficulty, int timeLimit, String a, String b, String c, String d){
        try{
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(color);
            builder.setFooter("Information generated at: " + date.toString(), (member == null ? "null" : member.getUser().getEffectiveAvatarUrl()));
            builder.setDescription("**Trivia for __" + member.getUser().getName() + "__** - React with one of the following letters to answer.")
                    .addField("Question:", "**" + question + "**", false)
                    .addBlankField(false)
                    .addField("Category:", category, true)
                    .addField("Difficulty:", difficulty, true)
                    .addField("Time Limit:",  timeLimit + " seconds", true)
                    .addBlankField(false)
                    .addField("Answers:", "**" + String.format("A. %s\nB. %s\nC. %s\nD. %s", a, b, c, d) + "**",false);

            return builder;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

}
