package me.synicallyevil.trivia;

import me.synicallyevil.evilbot.EvilBot;
import me.synicallyevil.evilbot.EvilBotJDA;
import me.synicallyevil.evilbot.EvilBotPlugin;
import me.synicallyevil.trivia.commands.TriviaCommand;
import me.synicallyevil.trivia.listeners.MessageEvents;
import me.synicallyevil.trivia.utils.Question;
import me.synicallyevil.trivia.utils.TriviaManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Trivia implements EvilBotPlugin {

    private TriviaManager triviaManager;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    //private List<Question> questions = new ArrayList<>();

    private List<Question> easyQuestions = new ArrayList<>();
    private List<Question> medQuestions = new ArrayList<>();
    private List<Question> hardQuestions = new ArrayList<>();

    private Map<Member, Question> playingTrivia = new HashMap<>();

    @Override
    public String pluginName() {
        return "Trivia";
    }

    @Override
    public String pluginVersion() {
        return "1.0";
    }

    @Override
    public String pluginAuthor() {
        return "SynicallyEvil";
    }

    @Override
    public void onLoad() {
        triviaManager = new TriviaManager(this);
        triviaManager.addNewQuestions(triviaManager.getAmount(), "easy");
        triviaManager.addNewQuestions(triviaManager.getAmount(), "medium");
        triviaManager.addNewQuestions(triviaManager.getAmount(), "hard");

        EvilBot.getBot().getApi().addCommand(new TriviaCommand(this));
        EvilBot.getBot().getApi().addListener(new MessageEvents(this));
    }

    public List<Question> getEasyQuestions() {
        return easyQuestions;
    }

    public List<Question> getMedQuestions() {
        return medQuestions;
    }

    public List<Question> getHardQuestions() {
        return hardQuestions;
    }

    public Map<Member, Question> getPlayingTrivia() {
        return playingTrivia;
    }

    public TriviaManager getTriviaManager() {
        return triviaManager;
    }

    public void setupRunnable(Runnable run, int period){
        executor.scheduleAtFixedRate(run, 0, period, TimeUnit.SECONDS);
    }
}
