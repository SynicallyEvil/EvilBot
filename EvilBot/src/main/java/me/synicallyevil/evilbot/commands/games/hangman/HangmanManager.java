package me.synicallyevil.evilbot.commands.games.hangman;

import java.util.ArrayList;
import java.util.List;

public class HangmanManager {

    private List<Long> players = new ArrayList<>();
    private List<Character> usedCharacters = new ArrayList<>();
    private Long messageID, textChannelID;

    public HangmanManager(Long playerID, Long messageID, Long textChannelID){
        this.messageID = messageID;
        this.textChannelID = textChannelID;

        players.add(playerID);
    }

    public Long getTextChannelID() {
        return textChannelID;
    }

    public Long getMessageID() {
        return messageID;
    }

    public List<Long> getPlayers() {
        return players;
    }

    public List<Character> getUsedCharacters() {
        return usedCharacters;
    }
}
