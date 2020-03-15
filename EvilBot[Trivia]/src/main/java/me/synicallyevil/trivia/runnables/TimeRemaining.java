package me.synicallyevil.trivia.runnables;

import me.synicallyevil.trivia.Trivia;
import me.synicallyevil.trivia.utils.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeRemaining implements Runnable {

    Trivia trivia;
    Question question;
    Member member;
    MessageReceivedEvent event;

    List<String> answers = new ArrayList<>();

    public TimeRemaining(Trivia trivia, Question question, Member member, MessageReceivedEvent event){
        this.trivia = trivia;
        this.question = question;
        this.member = member;
        this.event = event;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            int i = trivia.getPlayingTrivia().get(member).getTimeRemaining();

            System.out.println("Time: " + i);

            if(!(trivia.getPlayingTrivia().containsKey(member))) {
                System.out.println("Not playing I guess.");
                Thread.currentThread().interrupt();
                return;
            }

            if(i < 0){
                System.out.println("Time's less than 0 apparently.");
                Thread.currentThread().interrupt();
                return;
            }

            EmbedBuilder eBuilder = new EmbedBuilder();
            eBuilder.setColor(question.getDifficultyColor());
            eBuilder.setFooter("Information generated at: " + event.getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), member.getUser().getEffectiveAvatarUrl());
            eBuilder.setDescription("**Trivia for __" + member.getUser().getName() + "__** - React with one of the following letters to answer.")
                    .addField("Question:", "**" + question.getQuestion() + "**", false)
                    .addBlankField(false)
                    .addField("Category:", question.getCategory(), true)
                    .addField("Difficulty:", question.getDifficulty(), true)
                    .addField("Time Remaining:", i + " second" + (i != 1 ? "s" : ""), true)
                    .addBlankField(false)
                    .addField("Answers:", "**" + (answers.size() < 3 ? "True or False" : String.format("A. %s\nB. %s\nC. %s\nD. %s", answers.get(0), answers.get(1), answers.get(2), answers.get(3))) + "**",false);

            event.getChannel().editMessageById(trivia.getPlayingTrivia().get(member).getMessageId(), eBuilder.build()).complete();
            trivia.getPlayingTrivia().get(member).reduceTimeRemaining();
        }
    }

}
