package me.synicallyevil.trivia.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.synicallyevil.trivia.Trivia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriviaManager {

    Trivia trivia;
    private int amount = 50;

    public TriviaManager(Trivia trivia, int amount){
        this.trivia = trivia;
        this.amount = amount;
    }

    public TriviaManager(Trivia trivia){
        this.trivia = trivia;
    }

    public void addNewQuestions(int amount, String difficulty){
        System.out.println("Adding " + amount + " more questions to difficulty [" + difficulty + "]..");

        try{
            URL url = new URL("https://opentdb.com/api.php?amount=" + amount + "&type=multiple&difficulty=" + difficulty);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder builder = new StringBuilder();
            for(int line; (line = in.read()) >= 0;)
                builder.append((char)line);

            String o;
            String output = builder.toString();
            do {
                o = output;
                output = URLDecoder.decode(o, "UTF-8");

            } while (!o.equals(output));

            JsonObject json = new JsonParser().parse(output).getAsJsonObject();
            JsonArray results = json.get("results").getAsJsonArray();

            for(JsonElement result : results){
                JsonObject r = result.getAsJsonObject();

                String category = r.get("category").getAsString();
                String qDifficulty = r.get("difficulty").getAsString().substring(0, 1).toUpperCase() + r.get("difficulty").getAsString().substring(1);
                String question = getBetterString(r.get("question").getAsString());
                String correct = getBetterString(r.get("correct_answer").getAsString());

                List<String> incorrect = new ArrayList<>();
                for(JsonElement i : r.get("incorrect_answers").getAsJsonArray())
                    incorrect.add(getBetterString(i.getAsString()));

                if(difficulty.equals("easy"))
                    trivia.getEasyQuestions().add(new Question(category, qDifficulty, question, correct, incorrect, null));
                if(difficulty.equals("medium"))
                    trivia.getMedQuestions().add(new Question(category, qDifficulty, question, correct, incorrect, null));
                if(difficulty.equals("hard"))
                    trivia.getHardQuestions().add(new Question(category, qDifficulty, question, correct, incorrect, null));
            }
        }catch(Exception ex){
            //ex.getMessage();
            //ex.printStackTrace();
        }

        //System.out.println("Done. Now have a loaded amount of " + trivia.getQuestions().size() + " trivia questions!");
        if(difficulty.equalsIgnoreCase("easy"))
            System.out.println("Done. Now have a loaded amount of " + trivia.getEasyQuestions().size() + " [easy] trivia questions!");
        if(difficulty.equalsIgnoreCase("medium"))
            System.out.println("Done. Now have a loaded amount of " + trivia.getMedQuestions().size() + " [medium] trivia questions!");
        if(difficulty.equalsIgnoreCase("hard"))
            System.out.println("Done. Now have a loaded amount of " + trivia.getHardQuestions().size() + " [hard] trivia questions!");
    }

    public int getAmount() {
        return amount;
    }

    public Question getQuestion(String difficulty){
        Question question = null;
        String[] difficulties = {"easy", "medium", "hard"};

        if(difficulty.equalsIgnoreCase("all") || !Arrays.asList(difficulties).contains(difficulty.toLowerCase())){
            List<Question> randomQuestion = new ArrayList<>();
            randomQuestion.addAll(trivia.getEasyQuestions());
            randomQuestion.addAll(trivia.getMedQuestions());
            randomQuestion.addAll(trivia.getHardQuestions());

            if(randomQuestion.size() < 10){
                addNewQuestions(amount, "easy");
                addNewQuestions(amount, "medium");
                addNewQuestions(amount, "hard");
            }

            Collections.shuffle(randomQuestion);
            question = randomQuestion.get(0);

            if(question.getDifficulty().equalsIgnoreCase("easy")){
                trivia.getEasyQuestions().remove(question);
            }else if(question.getDifficulty().equalsIgnoreCase("medium")){
                trivia.getMedQuestions().remove(question);
            }else if(question.getDifficulty().equalsIgnoreCase("hard")){
                trivia.getHardQuestions().remove(question);
            }

            return randomQuestion.get(0);
        }

        if(difficulty.equalsIgnoreCase("easy")) {
            if(trivia.getEasyQuestions().size() < 10)
                addNewQuestions(amount, difficulty);

            question = trivia.getEasyQuestions().get(0);
            trivia.getEasyQuestions().remove(question);
        }

        if(difficulty.equalsIgnoreCase("medium")){
            if(trivia.getEasyQuestions().size() < 10)
                addNewQuestions(amount, difficulty);

            question = trivia.getMedQuestions().get(0);
            trivia.getMedQuestions().remove(question);
        }

        if(difficulty.equalsIgnoreCase("hard")){
            if(trivia.getEasyQuestions().size() < 10)
                addNewQuestions(amount, difficulty);

            question = trivia.getHardQuestions().get(0);
            trivia.getHardQuestions().remove(question);
        }

        return question;
    }

    private String getBetterString(String message){
        return message.replace("&#039;", "'")
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&Uuml;", "Ü")
                .replace("&ocirc;", "ô")
                .replace("&eacute;", "é");
    }
}
