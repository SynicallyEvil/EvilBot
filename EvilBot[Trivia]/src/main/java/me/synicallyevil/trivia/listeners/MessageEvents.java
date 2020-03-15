package me.synicallyevil.trivia.listeners;

import me.synicallyevil.trivia.Trivia;
import me.synicallyevil.trivia.utils.Question;
import me.synicallyevil.trivia.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
            if (!(event.getChannel() == q.getChannel()) || !(event.getMessageId().equals(q.getMessageId())))
                return;

            int selectedWrong = -1;
            if(!(event.getReactionEmote().getName().equals(q.getUnicode()))) {
                if(event.getReactionEmote().getName().equals("\uD83C\uDDE6"))
                    selectedWrong = 0;
                else if(event.getReactionEmote().getName().equals("\uD83C\uDDE7"))
                    selectedWrong = 1;
                else if(event.getReactionEmote().getName().equals("\uD83C\uDDE8"))
                    selectedWrong = 2;
                else if(event.getReactionEmote().getName().equals("\uD83C\uDDE9"))
                    selectedWrong = 3;
            }

            List<String> answers = q.getShuffledAnswers();
            String a = (answers.get(0).equals(q.getCorrect()) ? answers.get(0) + " ✅" : "~~" + answers.get(0) + "~~" + (selectedWrong == 0 ? " ❌" : ""));
            String b = (answers.get(1).equals(q.getCorrect()) ? answers.get(1) + " ✅" : "~~" + answers.get(1) + "~~" + (selectedWrong == 1 ? " ❌" : ""));
            String c = (answers.get(2).equals(q.getCorrect()) ? answers.get(2) + " ✅" : "~~" + answers.get(2) + "~~" + (selectedWrong == 2 ? " ❌" : ""));
            String d = (answers.get(3).equals(q.getCorrect()) ? answers.get(3) + " ✅" : "~~" + answers.get(3) + "~~" + (selectedWrong == 3 ? " ❌" : ""));

            EmbedBuilder builder = Utils.triviaBuilder(q.getDifficultyColor(), q.getDate(), q.getMember(), q.getQuestion(), q.getCategory(), q.getDifficulty(), q.getTimeLimit(), a, b, c, d);
            event.getChannel().editMessageById(q.getMessageId(), builder.build()).complete();
            trivia.getPlayingTrivia().remove(event.getMember());

        }
    }
}
