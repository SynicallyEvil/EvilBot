package me.synicallyevil.trivia.listeners;

import me.synicallyevil.trivia.Trivia;
import me.synicallyevil.trivia.utils.Question;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageEvents extends ListenerAdapter {

    Trivia trivia;

    public MessageEvents(Trivia trivia){
        this.trivia = trivia;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        if(trivia.getPlayingTrivia().containsKey(event.getMember())) {
            Question q = trivia.getPlayingTrivia().get(event.getMember());
            if (!(event.getChannel() == q.getChannel()) && !(event.getMessageId().equals(q.getMessageId())))
                return;

            int selectedWrong = -1;

            if(event.getReactionEmote().getName().equals(q.getUnicode())) {
                //event.getChannel().sendMessage("Correct.").complete();
            }else {
                //event.getChannel().sendMessage("Incorrect. Correct answer was: " + q.getUnicode()).complete();
                String uA = "\uD83C\uDDE6";
                String uB = "\uD83C\uDDE7";
                String uC = "\uD83C\uDDE8";
                String uD = "\uD83C\uDDE9";

                if(event.getReactionEmote().getName().equals(uA))
                    selectedWrong = 0;
                else if(event.getReactionEmote().getName().equals(uB))
                    selectedWrong = 1;
                else if(event.getReactionEmote().getName().equals(uC))
                    selectedWrong = 2;
                else if(event.getReactionEmote().getName().equals(uD))
                    selectedWrong = 3;

            }

            List<String> answers = q.getShuffledAnswers();

            String a = (answers.get(0).equals(q.getCorrect()) ? answers.get(0) + " ✅" : "~~" + answers.get(0) + "~~" + (selectedWrong == 0 ? " ❌" : ""));
            String b = (answers.get(1).equals(q.getCorrect()) ? answers.get(1) + " ✅" : "~~" + answers.get(1) + "~~" + (selectedWrong == 1 ? " ❌" : ""));
            String c = (answers.get(2).equals(q.getCorrect()) ? answers.get(2) + " ✅" : "~~" + answers.get(2) + "~~" + (selectedWrong == 2 ? " ❌" : ""));
            String d = (answers.get(3).equals(q.getCorrect()) ? answers.get(3) + " ✅" : "~~" + answers.get(3) + "~~" + (selectedWrong == 3 ? " ❌" : ""));

            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(q.getDifficultyColor());
            builder.setFooter("Information generated at: " + q.getDate().toString(), (event.getMember() == null ? "null" : event.getMember().getUser().getEffectiveAvatarUrl()));
            builder.setDescription("**Trivia for __" + event.getMember().getUser().getName() + "__** - React with one of the following letters to answer.")
                    .addField("Question:", "**" + q.getQuestion() + "**", false)
                    .addBlankField(false)
                    .addField("Category:", q.getCategory(), true)
                    .addField("Difficulty:", q.getDifficulty(), true)
                    .addField("Time Limit:", "10 seconds", true)
                    .addBlankField(false)
                    .addField("Answers:", "**" + (answers.size() < 3 ? "True or False" : String.format("A. %s\nB. %s\nC. %s\nD. %s", a, b, c, d)) + "**",false);

            event.getChannel().editMessageById(q.getMessageId(), builder.build()).complete();
            trivia.getPlayingTrivia().remove(event.getMember());

        }
    }

}
