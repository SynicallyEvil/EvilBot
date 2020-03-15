package me.synicallyevil.trivia.utils;

import me.synicallyevil.trivia.Trivia;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Question {

    private String category;
    private String difficulty;
    private String question;
    private String correct;
    private List<String> incorrect;

    private Member member;
    private MessageReceivedEvent event;
    private Trivia trivia;

    private List<String> shuffledAnswers = new ArrayList<>();

    private String correctLetter;
    private TextChannel channel;
    private String messageid;
    private String unicode;

    private Date date;

    private int timeLimit = 10;
    private int timeRemaining;

    public Question(String category, String difficulty, String question, String correct, List<String> incorrect, String messageid) {
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.correct = correct;
        this.incorrect = incorrect;
        this.messageid = messageid;

        shuffledAnswers.add(correct);
        shuffledAnswers.addAll(incorrect);
        Collections.shuffle(shuffledAnswers);
    }

    public Question(Question question, String correctLetter, TextChannel channel, String unicode, List<String> shuffledAnswers, Trivia trivia, Date date, Member member, MessageReceivedEvent event) {
        this.category = question.getCategory();
        this.difficulty = question.getDifficulty();
        this.question = question.getQuestion();
        this.correct = question.getCorrect();
        this.incorrect = question.getIncorrect();
        this.correctLetter = correctLetter;
        this.channel = channel;
        this.unicode = unicode;
        this.shuffledAnswers = shuffledAnswers;
        this.trivia = trivia;
        this.date = date;
        this.member = member;
        this.event = event;

        timeRemaining = timeLimit;
        runnable();
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrect() {
        return correct;
    }

    public List<String> getIncorrect() {
        return incorrect;
    }

    public boolean isCorrect(String letter) {
        return letter.equalsIgnoreCase(this.correctLetter);
    }

    public String getCorrectLetter() {
        return correctLetter.toUpperCase();
    }

    public List<String> getShuffledAnswers() {
        return shuffledAnswers;
    }

    public Color getDifficultyColor() {
        return (difficulty.equalsIgnoreCase("hard") ? Color.RED : (difficulty.equalsIgnoreCase("medium") ? Color.YELLOW : Color.GREEN));
    }

    public void reduceTimeRemaining() {
        this.timeRemaining -= 1;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public String getMessageId() {
        return messageid;
    }

    public void setMessageid(String id) {
        this.messageid = id;
    }

    public Date getDate() {
        return date;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public Member getMember() {
        return member;
    }

    public int getTimeLimit(){
        return timeLimit;
    }

    public void runnable() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            try{ // 
                int i = timeRemaining;

                if(!(trivia.getPlayingTrivia().containsKey(member))) {
                    System.out.println("Not playing I guess. Finished?");
                    exec.shutdown();
                    return;
                }

                if (i < 0) {
                    System.out.println("Time's less than 0 apparently.");
                    event.getChannel().sendMessage("Times up, " + member.getAsMention() + "!").complete();
                    trivia.getPlayingTrivia().remove(member);

                    String a = (shuffledAnswers.get(0).equals(correct) ? shuffledAnswers.get(0) + " ✅" : "~~" + shuffledAnswers.get(0) + "~~" + " ❌");
                    String b = (shuffledAnswers.get(1).equals(correct) ? shuffledAnswers.get(1) + " ✅" : "~~" + shuffledAnswers.get(1) + "~~" + " ❌");
                    String c = (shuffledAnswers.get(2).equals(correct) ? shuffledAnswers.get(2) + " ✅" : "~~" + shuffledAnswers.get(2) + "~~" + " ❌");
                    String d = (shuffledAnswers.get(3).equals(correct) ? shuffledAnswers.get(3) + " ✅" : "~~" + shuffledAnswers.get(3) + "~~" + " ❌");

                    /*EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(getDifficultyColor());
                    builder.setFooter("Information generated at: " + date.toString(), (event.getMember() == null ? "null" : event.getMember().getUser().getEffectiveAvatarUrl()));
                    builder.setDescription("**Trivia for __" + event.getMember().getUser().getName() + "__** - React with one of the following letters to answer.")
                            .addField("Question:", "**" + question + "**", false)
                            .addBlankField(false)
                            .addField("Category:", category, true)
                            .addField("Difficulty:", difficulty, true)
                            .addField("Time Limit:",  "10 seconds", true)
                            .addBlankField(false)
                            .addField("Answers:", "**" + String.format("A. %s\nB. %s\nC. %s\nD. %s", a, b, c, d) + "**",false);*/
                    EmbedBuilder builder = Utils.triviaBuilder(getDifficultyColor(), date, member, question, category, difficulty, timeLimit, a, b, c, d);
                    event.getChannel().editMessageById(messageid, builder.build()).complete();
                    exec.shutdown();
                    return;
                }

                reduceTimeRemaining();
            }catch(Exception ignored){}
        }, 0, 1, TimeUnit.SECONDS);
    }
}