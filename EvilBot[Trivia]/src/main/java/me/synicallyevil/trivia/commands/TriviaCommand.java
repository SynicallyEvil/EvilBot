package me.synicallyevil.trivia.commands;

import me.synicallyevil.evilbot.commands.Command;
import me.synicallyevil.evilbot.commands.CommandHandler;
import me.synicallyevil.trivia.Trivia;
import me.synicallyevil.trivia.utils.Question;
import me.synicallyevil.trivia.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TriviaCommand implements Command {

    private boolean enabled = true;
    private Trivia trivia;

    String a = "\uD83C\uDDE6";
    String b = "\uD83C\uDDE7";
    String c = "\uD83C\uDDE8";
    String d = "\uD83C\uDDE9";

    String t = "\uD83C\uDDF9";
    String f = "\uD83C\uDDEB";

    public TriviaCommand(Trivia trivia){
        this.trivia = trivia;
    }

    @Override
    public String getCommand() {
        return "trivia";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean state) {
        this.enabled = state;
    }

    @Override
    public void onCommand(String[] args, CommandHandler handler) {
        if(trivia.getPlayingTrivia().containsKey(handler.getMember())){
            handler.sendMessage("You already have a trivia question in progress.");
            return;
        }

        String difficulty = "all";
        if(args.length > 1 && args[1] != null && !args[1].isEmpty())
            difficulty = args[1].toLowerCase();

        trivia.getPlayingTrivia().remove(handler.getMember());
        Question question = trivia.getTriviaManager().getQuestion(difficulty);

        // Randomizes the list of answers.
        List<String> answers = question.getShuffledAnswers();

        // Grabs the correct letter.
        String correctLetter = "";
        String unicode = "";

        for(int i = 0; i < answers.size(); i++){
            if(!(answers.get(i).equalsIgnoreCase(question.getCorrect())))
                continue;

            switch(i){
                case 1:
                    correctLetter = (answers.size() > 2 ? "B" : "F");
                    unicode = (answers.size() > 2 ? b : f);
                    break;
                case 2:
                    correctLetter = "C";
                    unicode = c;
                    break;
                case 3:
                    correctLetter = "D";
                    unicode = d;
                    break;
                default:
                    correctLetter = (answers.size() > 2 ? "A" : "T");
                    unicode = (answers.size() > 2 ? a : t);
            }
        }

        Timestamp time = new Timestamp(System.currentTimeMillis());
        Date date = new Date(time.getTime());

        EmbedBuilder builder = Utils.triviaBuilder(question.getDifficultyColor(), date, handler.getMember(), question.getQuestion(), question.getCategory(), question.getDifficulty(), question.getTimeLimit(), answers.get(0), answers.get(1), answers.get(2), answers.get(3));
        handler.getEvent().getChannel().sendMessage(builder.build()).queue(message -> {
            message.addReaction(a).queue();
            message.addReaction(b).queue();
            message.addReaction(c).queue();
            message.addReaction(d).queue();

            if(trivia.getPlayingTrivia().containsKey(handler.getMember()))
                trivia.getPlayingTrivia().get(handler.getMember()).setMessageid(message.getId());
        });

        trivia.getPlayingTrivia().put(handler.getMember(), new Question(question, correctLetter, handler.getEvent().getTextChannel(), unicode, question.getShuffledAnswers(), trivia, date, handler.getMember(), handler.getEvent()));
        //trivia.setupRunnable(new TimeRemaining(trivia, question, handler.getMember(), handler.getEvent()), 1);

    } //String.format("A. %s\nB. %s\n", answers.get(0), answers.get(1)) + (answers.size() > 2 ? String.format("C. %s\nD. %s", answers.get(2), answers.get(3)) : "")
}
