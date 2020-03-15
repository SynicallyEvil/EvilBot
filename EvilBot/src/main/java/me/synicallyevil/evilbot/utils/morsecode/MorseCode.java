package me.synicallyevil.evilbot.utils.morsecode;

import java.util.HashMap;
import java.util.Map;

public class MorseCode {

    private Map<Character, String> codes = new HashMap<>();
    private String morsecode;
    private String message;
    private String revertedMessage;

    public MorseCode(boolean isText, String message){
        addAllCodes();

        if(isText){
            this.message = message;
            this.morsecode = convertToMorseCode();
            this.revertedMessage = convertToNormalMessage();
        }else{
            this.morsecode = message;
            this.message = convertToNormalMessage();
        }
    }

    public String getMorsecode(){
        return morsecode;
    }

    public String getMessage(){
        return message;
    }

    public String getRevertedMessage(){
        return revertedMessage;
    }

    public String convertToMorseCode(){
        String[] words = message.split(" ");

        StringBuilder builder = new StringBuilder();

        for(String word : words){

            for(int i = 0; i < word.length(); i++){
                char c = Character.toUpperCase(word.charAt(i));

                builder.append(codes.get(c)).append(" ");
            }

            builder.append("/ ");
        }

        builder.setLength(builder.length() - 3);

        return builder.toString();
    }

    public String convertToNormalMessage(){
        StringBuilder builder = new StringBuilder();

        String[] letters = morsecode.split(" ");

        for(int i = 0; i < letters.length; i++)
            builder.append(getCharacter(letters[i]));

        return builder.toString().replace("/", " ");
    }

    private String getCharacter(String morsecode){
        if(morsecode.equals("/"))
            return "/";

        for(Map.Entry<Character, String> entries : codes.entrySet()){
            if(morsecode.equals(entries.getValue()))
                return String.valueOf(entries.getKey());
        }

        return "N/A";
    }

    private void addAllCodes(){
        codes.clear();

        // Letters.
        codes.put('A', ".-");
        codes.put('B', "-...");
        codes.put('C', "-.-.");
        codes.put('D', "-..");
        codes.put('E', ".");
        codes.put('F', "..-.");
        codes.put('G', "--.");
        codes.put('H', "....");
        codes.put('I', "..");
        codes.put('J', ".---");
        codes.put('K', "-.-");
        codes.put('L', ".-..");
        codes.put('M', "--");
        codes.put('N', "-.");
        codes.put('O', "---");
        codes.put('P', ".--.");
        codes.put('Q', "--.-");
        codes.put('R', ".-.");
        codes.put('S', "...");
        codes.put('T', "-");
        codes.put('U', "..-");
        codes.put('V', "...-");
        codes.put('W', ".--");
        codes.put('X', "-..-");
        codes.put('Y', "-.--");
        codes.put('Z', "--..");

        // Numbers.
        codes.put('0', "-----");
        codes.put('1', ".----");
        codes.put('2', "..---");
        codes.put('3', "...--");
        codes.put('4', "....-");
        codes.put('5', ".....");
        codes.put('6', "-....");
        codes.put('7', "--...");
        codes.put('8', "---..");
        codes.put('9', "----.");

        // Symbols.
        codes.put('.', ".-.-.-");
        codes.put(',', "--..--");
        codes.put(':', "---...");
        codes.put(';', "-.-.-.");
        codes.put('?', "..--..");
        codes.put('\'', ".----.");
        codes.put('-', "-....-");
        codes.put('_', "..--.-");
        codes.put('/', "-..-.");
        codes.put('"', ".-..-.");
        codes.put('@', ".--.-.");
        codes.put('=', "-...-");
        codes.put('+', ".-.-.");
        codes.put('!', "---.");
        codes.put('$', "...-..-");
    }

}
